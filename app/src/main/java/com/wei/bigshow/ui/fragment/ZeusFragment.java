package com.wei.bigshow.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.zeus.GuideHeaderItem;
import com.wei.bigshow.model.zeus.PlotOptionItem;
import com.wei.bigshow.model.zeus.PlotSingleItem;
import com.wei.bigshow.rx.event.EditItemEvent;
import com.wei.bigshow.rx.event.GiphyTapEvent;
import com.wei.bigshow.ui.adapter.zeus.GuideHeaderView;
import com.wei.bigshow.ui.adapter.zeus.PlotOptionsView;
import com.wei.bigshow.ui.adapter.zeus.PlotSingleView;

import java.util.ArrayList;
import java.util.HashMap;

import io.nlopez.smartadapters.SmartAdapter;
import rx.functions.Action1;


/**
 * 具有上帝视角的故事编辑类
 * created by Adam Wei
 */
public class ZeusFragment extends BaseRecyclerFragment {


    private HashMap<Integer, Object> listMap = new HashMap<>();

    int lastSwipedPos = -1;

    public static ZeusFragment instance(Bundle bundle) {
        ZeusFragment fragment = new ZeusFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.common_list_normal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initToolbar();
        initView();
        initData();
        initEvent();
        setUpRecyclerView();
    }

    public void initToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(GuideHeaderItem.class, GuideHeaderView.class)
                .map(PlotSingleItem.class, PlotSingleView.class)
                .map(PlotOptionItem.class, PlotOptionsView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(false);
    }

    private void initData() {
        itemList = new ArrayList<>();
        itemList.add(new GuideHeaderItem());
        itemList.add(new PlotSingleItem());
        itemList.add(new PlotOptionItem());
        adapter.setItems(itemList);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_zeus_save, menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_zeus_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onMoreData() {
        // do nothing
    }

    @Override
    protected void onRefreshData() {
        // do nothing
    }

    public PlotSingleItem editPlotMeta = null;

    public void initEvent() {

        _subscriptions
                .add(_rxBus.toObserverable().subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof GiphyTapEvent) {
                            // here we come
                            editPlotMeta.src = ((GiphyTapEvent) event).url;
                            editPlotMeta.text = ((GiphyTapEvent) event).slug;
                            itemList.remove(editPlotMeta.pos);
                            itemList.add(editPlotMeta.pos, editPlotMeta);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), ((GiphyTapEvent) event).url, Toast.LENGTH_SHORT).show();

                        } else if (event instanceof PlotSingleItem) {
                            editPlotMeta = ((PlotSingleItem) event);

                        } else if (event instanceof EditItemEvent) {
                            String result = ((EditItemEvent) event).position + " : " + ((EditItemEvent) event).content;
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    private void setUpRecyclerView() {
        setScrollListener();
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
    }

    private void setScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.e("onScrollStop", " ");
                    if (lastSwipedPos != -1) {
                        adapter.notifyItemChanged(lastSwipedPos);
                        lastSwipedPos = -1;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 100) {
                    Log.e("onScroll Y", "dy: " + dy);
                }
            }
        });
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.ACTION_STATE_SWIPE) {
            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                xMark = ContextCompat.getDrawable(getContext(), R.drawable.ic_tab_2);
                xMark.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) getActivity().getResources().getDimension(R.dimen.dimen_16dp);
                initiated = true;
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                View itemView = viewHolder.itemView;

                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                View itemView = viewHolder.itemView;
//                Log.e("onSwiped", "x: " + itemView.getX() + " y:" + itemView.getY());
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                if(itemView.getX()<=-screenWidth){
                    itemView.setX(itemView.getX() + 100);
                }else if (itemView.getX()>=screenWidth){
                    itemView.setX(itemView.getX() - 100);
                }
                if(lastSwipedPos!=-1){
                    adapter.notifyItemChanged(lastSwipedPos);
                }
                lastSwipedPos = viewHolder.getAdapterPosition();

            }
            

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                if (!initiated) {
                    init();
                }


                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
                xMark.draw(c);



                if (itemView instanceof PlotSingleView) {
                    View foregroundView = ((PlotSingleView)itemView).getSwipedLayout();//slationX(-dX);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

}

package com.wei.bigshow.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private int edit_position = -1;
    private boolean flag_swiped = false;

    public static final int SWIPE_STATE_MID = 0;
    public static final int SWIPE_STATE_LEFT = 1;
    public static final int SWIPE_STATE_RIGHT = 2;
    public int swipe_state = 0;

    private Rect clickRegionRect;
    private Rect leftImageRegionRect, rightImageRegionRect;

    private int toolbarHeight = 0;

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

        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

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
        itemList.add(new PlotOptionItem());
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
        setTouchRecyclerView();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ClipItemTouchHelper(0, ItemTouchHelper.RIGHT ));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setTouchRecyclerView() {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("onTouch", "X: " + motionEvent.getRawX() + " Y: " + motionEvent.getRawY());
                    Point point = new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY() - toolbarHeight);

                    if (swipe_state == SWIPE_STATE_LEFT) {
                        if (clickRegionRect.contains(point.x, point.y)) {
                            if (leftImageRegionRect.contains(point.x, point.y)) {
                                Log.e("onTouch", " left Image ! pos: " + edit_position);

                                itemList.add(edit_position + 1, new PlotSingleItem());
                                adapter.notifyItemInserted(edit_position);

                            }

                            if (clickRegionRect.contains(point.x, point.y)) {
                                if (rightImageRegionRect.contains(point.x, point.y)) {
                                    Log.e("onTouch", " right Image ! pos: " + edit_position);
                                    recyclerView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            itemList.remove(edit_position);
                                            adapter.notifyItemRemoved(edit_position);
                                        }
                                    }, 300);

                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    public class ClipItemTouchHelper extends ItemTouchHelper.SimpleCallback {

        public ClipItemTouchHelper(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.4f;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof PlotSingleView) {
                int position = viewHolder.getAdapterPosition();
                if (position != edit_position) {
                    if (edit_position != -1 && flag_swiped == true) {
                        adapter.notifyItemChanged(edit_position);
                        flag_swiped = false;
                        edit_position = position;
                    }
                }
            }
            return super.getSwipeDirs(recyclerView, viewHolder);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (viewHolder.itemView instanceof PlotSingleView) {

                flag_swiped = true;
                edit_position = viewHolder.getAdapterPosition();

                PlotSingleView itemView = (PlotSingleView) viewHolder.itemView;
                ImageView addView = itemView.ivAdd;
                ImageView delView = itemView.ivDelete;

                clickRegionRect = new Rect(itemView.getLeft(),
                        itemView.getTop() + (int) recyclerView.getY(),
                        itemView.getRight(),
                        itemView.getBottom() + (int) recyclerView.getY());

                leftImageRegionRect = new Rect(addView.getLeft() + clickRegionRect.left,
                        addView.getTop() + clickRegionRect.top,
                        addView.getRight() + clickRegionRect.left,
                        addView.getBottom() + clickRegionRect.top);

                rightImageRegionRect = new Rect(delView.getLeft() + clickRegionRect.left,
                        delView.getTop() + clickRegionRect.top,
                        delView.getRight() + clickRegionRect.left,
                        delView.getBottom() + clickRegionRect.top);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (viewHolder != null && viewHolder.itemView instanceof PlotSingleView) {
                final View foregroundView = ((PlotSingleView) viewHolder.itemView).clipForeground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
            if (viewHolder.itemView instanceof PlotSingleView) {
                final View foregroundView = ((PlotSingleView) viewHolder.itemView).clipForeground;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);

                drawBackground(viewHolder, dX, actionState);

                if (dX >= getResources().getDisplayMetrics().widthPixels) {
                    if (swipe_state != SWIPE_STATE_LEFT) {
                        swipe_state = SWIPE_STATE_LEFT;
                    }
                    Log.d("onChildDraw", " dx > screenWidth" + "  flag_swiped: " + flag_swiped);
                } else if (dX <= -getResources().getDisplayMetrics().widthPixels) {
                    if (swipe_state != SWIPE_STATE_RIGHT) {
                        swipe_state = SWIPE_STATE_RIGHT;
                    }
                    Log.d("onChildDraw", " dx < screenWidth" + "  flag_swiped: " + flag_swiped);
                } else if (dX == 0) {
                    if (swipe_state != SWIPE_STATE_MID) {
                        swipe_state = SWIPE_STATE_MID;
                    }
                }
            }
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
            if (viewHolder.itemView instanceof PlotSingleView) {
                final View foregroundView = ((PlotSingleView) viewHolder.itemView).clipForeground;

                drawBackground(viewHolder, dX, actionState);

                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            if (viewHolder.itemView instanceof PlotSingleView) {
                final View backgroundView = ((PlotSingleView) viewHolder.itemView).leftBackground;
                final View foregroundView = ((PlotSingleView) viewHolder.itemView).clipForeground;

                backgroundView.setRight(0);

                getDefaultUIUtil().clearView(foregroundView);
            }
        }

        private void drawBackground(RecyclerView.ViewHolder viewHolder, float dX, int actionState) {
            final View backgroundView = ((PlotSingleView) viewHolder.itemView).leftBackground;

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                backgroundView.setRight((int) Math.max(dX, 0));
            }
        }
    }

}

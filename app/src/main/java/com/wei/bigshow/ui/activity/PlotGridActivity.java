package com.wei.bigshow.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseListActivity;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.ui.adapter.PlotGridRootView;
import com.wei.bigshow.ui.adapter.PlotGridSubView;
import com.wei.bigshow.ui.view.PlotLinesView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午1:19
 */
public class PlotGridActivity extends BaseListActivity {

    @Bind(R.id.view_plot_lines)
    PlotLinesView view_plot_lines;

    float currentLines[][] = new float[8][4];

    HashMap<Integer, Object> dataMap = new HashMap<>();

    float itemHeight;// = getResources().getDisplayMetrics().density * 160 + 0.5f;
    float per2Width;// = getResources().getDisplayMetrics().widthPixels / 2;

    GridLayoutManager layoutManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_plot_grid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemHeight = getResources().getDisplayMetrics().density * 160 + 0.5f;
        per2Width = getResources().getDisplayMetrics().widthPixels / 2;

        layoutManager = createGridLayoutManager(2);

        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(GiphyEntity.class, PlotGridRootView.class)
                .map(GiphyEntity.class, PlotGridSubView.class)
                .listener(this)
                .builder(adapterLayoutBuilder)
                .into(recyclerView);
        initActionbar(null);
        swipeRefreshLayout.setEnabled(false);

        initData();
    }

    /**
     * 这里的返回值是跨度，如果Gridview设置spanCount为3， 则返回3是最长的宽度
     */
    public GridLayoutManager createGridLayoutManager(final int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(this, spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int TYPE_GRID = Mapper.viewTypeFromViewClass(PlotGridSubView.class);
                int type = adapter.getItemViewType(position);
                if (TYPE_GRID == type) {
                    return 1;
                } else {
                    return spanCount;
                }
            }
        });
        return manager;
    }

    /**
     * 处理相同的Entity但是不同的View
     */
    protected DefaultBindableLayoutBuilder adapterLayoutBuilder = new DefaultBindableLayoutBuilder() {
        @Override
        public Class<? extends BindableLayout> viewType(
                @NonNull Object item, int position, @NonNull Mapper mapper) {
            if (item instanceof GiphyEntity) {
                if (position == 0) {
                    return PlotGridRootView.class;
                } else {
                    return PlotGridSubView.class;
                }
            }
            return super.viewType(item, position, mapper);
        }

        @Override
        public boolean allowsMultimapping() {
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onMoreData() {

    }

    public void initData() {
        itemList = new ArrayList();
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        itemList.add(new GiphyEntity());
        adapter.setItems(itemList);

        dataMap = new HashMap<>();
        dataMap.put(1, new GiphyEntity());
        dataMap.put(2, new GiphyEntity());
        dataMap.put(3, new GiphyEntity());
        dataMap.put(4, new GiphyEntity());
        dataMap.put(5, new GiphyEntity());
        dataMap.put(8, new GiphyEntity());
        dataMap.put(9, new GiphyEntity());

        createLines();
    }

    public void createLines() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int offset = recyclerView.computeVerticalScrollOffset();
                int extent = recyclerView.computeVerticalScrollExtent();
                int range = recyclerView.computeVerticalScrollRange();
                Log.e("FFF", "offset: " + offset +
                        "\nextent: " + extent + "\nrange: " + range);
                Log.e("FFF", "dx: " + dx + "  dy: " + dy);

                int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePos = layoutManager.findLastVisibleItemPosition();

                Log.e("NOW", "firstVisible: " + firstVisiblePos + "  lastVisible: " + lastVisiblePos);
                if (layoutManager.getChildAt(firstVisiblePos) != null && layoutManager.getChildAt(lastVisiblePos) != null) {
                    Log.e("NOW", "firstTop: " + layoutManager.getChildAt(firstVisiblePos).getTop()
                            + "  lastTop: " + layoutManager.getChildAt(lastVisiblePos).getTop());
                }

                //first visible offset
                float mOffset = -offset;

                if (firstVisiblePos == 0) {
                    currentLines[0] = new float[]{per2Width, itemHeight / 2 + mOffset, per2Width / 2, itemHeight * 1.5f + mOffset};
                    currentLines[1] = new float[]{per2Width, itemHeight / 2 + mOffset, per2Width / 2 * 3, itemHeight * 1.5f + mOffset};
                } else {
                    float topItemsHeight = (firstVisiblePos+1/2)*itemHeight/2;
                    mOffset = mOffset + topItemsHeight;
                    currentLines[0] = new float[]{per2Width / 2, itemHeight * 0.5f + mOffset, per2Width / 2, itemHeight * 1.5f + mOffset};
                    currentLines[1] = new float[]{per2Width / 2, itemHeight * 0.5f + mOffset, per2Width / 2 * 3, itemHeight * 1.5f + mOffset};
                }



                currentLines[2] = new float[]{per2Width / 2, itemHeight * 1.5f + mOffset, per2Width / 2, itemHeight * 2.5f + mOffset};
                currentLines[3] = new float[]{per2Width / 2, itemHeight * 1.5f + mOffset, per2Width / 2 * 3, itemHeight * 2.5f + mOffset};
                currentLines[4] = new float[]{per2Width / 2, itemHeight * 2.5f + mOffset, per2Width / 2, itemHeight * 3.5f + mOffset};
                currentLines[5] = new float[]{per2Width / 2, itemHeight * 2.5f + mOffset, per2Width / 2 * 3, itemHeight * 3.5f + mOffset};

                currentLines[6] = new float[]{per2Width / 2, itemHeight * 3.5f + mOffset, per2Width / 2, itemHeight * 4.5f + mOffset};
                currentLines[7] = new float[]{per2Width / 2, itemHeight * 3.5f + mOffset, per2Width / 2 * 3, itemHeight * 4.5f + mOffset};

                view_plot_lines.refreshLines(currentLines);
                view_plot_lines.invalidate();
            }
        });

    }
}

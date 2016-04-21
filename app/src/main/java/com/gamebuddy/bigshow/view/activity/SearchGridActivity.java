package com.gamebuddy.bigshow.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseListActivity;
import com.gamebuddy.bigshow.core.net.ApiManager;
import com.gamebuddy.bigshow.model.network.GiphyEntity;
import com.gamebuddy.bigshow.view.adapter.SearchItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;
import rx.Subscriber;

/**
 * 其他用户的User界面
 * created by tindle
 * created time 16/4/13 下午5:35
 */
public class SearchGridActivity extends BaseListActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.base_activity_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayoutManager layoutmanager = createGridLayoutManager(2);
        recyclerView.setLayoutManager(layoutmanager);
        adapter = SmartAdapter.empty()
                .map(GiphyEntity.class, SearchItemView.class)
                .listener(this)
                .builder(adapterLayoutBuilder)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(false);
        initActionbar("");
        lazyLoad();
    }

    @Override
    protected void onRefreshData() {
        lazyLoad();
    }

    @Override
    protected void onMoreData() {

    }

    public void lazyLoad() {
        Map<String, String> map = new HashMap<>();
        map.put("rating", "g");
        map.put("limit", "20");
        map.put("q", "superman");

        mSubscription = ApiManager.getInstance().getSearchData(new Subscriber<List<GiphyEntity>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "on Completed !");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "on Error !");
            }

            @Override
            public void onNext(List<GiphyEntity> list) {
                Log.e(TAG, "on Next !");
                try {
                    if (list.size() == 0) {
                        Log.e(TAG, "on size 0 !");
                        return;
                    }
                    itemList.addAll(list);
                    adapter.setItems(itemList);

                } catch (Exception e) {
                }
            }
        }, map);

    }

    /**
     * 这里的返回值是跨度，如果Gridview设置spanCount为3， 则返回3是最长的宽度
     */
    public GridLayoutManager createGridLayoutManager(final int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(this, spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int TYPE_GRID = Mapper.viewTypeFromViewClass(SearchItemView.class);
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
            return super.viewType(item, position, mapper);
        }

        @Override
        public boolean allowsMultimapping() {
            return true;
        }
    };
}

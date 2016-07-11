package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.story.PlotMeta;
import com.wei.bigshow.model.story.PlotOptions;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.model.zeus.GuideHeaderItem;
import com.wei.bigshow.ui.adapter.BadgeItemView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.adapter.zeus.PlotMetaView;

import java.util.ArrayList;

import io.nlopez.smartadapters.SmartAdapter;

/**
 * Zeus 跳转 图片搜索类
 * created by Adam Wei
 */
public class GiphySearchFragment extends BaseRecyclerFragment{

    public static GiphySearchFragment instance(Bundle bundle) {
        GiphySearchFragment fragment = new GiphySearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(PlotMeta.class, PlotMetaView.class)
                .map(GiphyEntity.class, BadgeItemView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void initData() {
        itemList = new ArrayList<>();
        itemList.add(new GuideHeaderItem());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        adapter.setItems(itemList);
    }


    @Override
    protected void onMoreData() {
        // do nothing
    }

    @Override
    protected void onRefreshData() {
        // do nothing
    }
}

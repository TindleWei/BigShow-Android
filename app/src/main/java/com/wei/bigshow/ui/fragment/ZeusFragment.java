package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.story.PlotMeta;
import com.wei.bigshow.model.story.PlotOptions;
import com.wei.bigshow.model.zeus.GuideHeaderItem;
import com.wei.bigshow.ui.adapter.zeus.GuideHeaderView;
import com.wei.bigshow.ui.adapter.zeus.PlotMetaView;
import com.wei.bigshow.ui.adapter.zeus.PlotOptionsView;

import java.util.ArrayList;

import io.nlopez.smartadapters.SmartAdapter;

/**
 * 具有上帝视角的故事编辑类
 * created by Adam Wei
 */
public class ZeusFragment extends BaseRecyclerFragment{

    public static ZeusFragment instance(Bundle bundle) {
        ZeusFragment  fragment = new ZeusFragment();
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
                .map(GuideHeaderItem.class, GuideHeaderView.class)
                .map(PlotMeta.class, PlotMetaView.class)
                .map(PlotOptions.class, PlotOptionsView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(false);
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

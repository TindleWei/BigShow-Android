package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.BadgeItemView;
import com.wei.bigshow.ui.adapter.LoadMoreView;

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
                .map(GiphyEntity.class, BadgeItemView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void initData() {
        itemList = new ArrayList<>();
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

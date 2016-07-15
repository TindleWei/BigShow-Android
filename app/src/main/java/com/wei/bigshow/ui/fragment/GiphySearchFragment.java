package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.story.PlotMeta;
import com.wei.bigshow.model.view.LoadViewEntity;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.common_list_normal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_giphy_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
//        mListener.onInitSearchView(menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem gridMenuItem = menu.findItem(R.id.action_search);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            gridMenuItem.setIcon(R.mipmap.ic_action_search);
            gridMenuItem.setTitle("fuck1");
        } else {
            gridMenuItem.setIcon(R.mipmap.ic_action_search);
            gridMenuItem.setTitle("fuck2");
        }
    }


    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(PlotMeta.class, PlotMetaView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void initData() {
        itemList = new ArrayList<>();
        itemList.add(new PlotMeta());
        itemList.add(new PlotMeta());
        itemList.add(new PlotMeta());
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

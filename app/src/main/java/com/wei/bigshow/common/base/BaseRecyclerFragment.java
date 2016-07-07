package com.wei.bigshow.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wei.bigshow.R;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.listener.EndlessRecyclerOnScrollListener;
import com.wei.bigshow.ui.vandor.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;

/**
 * created by weizepeng
 */
public abstract class BaseRecyclerFragment extends BaseFragment {

    protected RecyclerMultiAdapter adapter;

    @Bind(R.id.multi_state_view)
    protected MultiStateView multiStateView;

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected List itemList = new ArrayList();
    protected LoadViewEntity loadmoreEntity;
    protected boolean isLoadingMore = false;
    protected boolean hasNoMoreData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_list_normal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.google_red,
                R.color.google_yellow,
                R.color.google_green,
                R.color.google_blue
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();
            }
        });
    }

    public void initLoadMoreLayout(LinearLayoutManager layoutmanager) {
        loadmoreEntity = new LoadViewEntity();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutmanager) {
            @Override
            public void onLoadMore() {
                if (isLoadingMore == false) {
                    onMoreData();
                }
            }
        });
    }

    public void resetDataLoadLayout() {
        if(isLoadingMore){
            adapter.delItem(loadmoreEntity);
        }else{
            swipeRefreshLayout.setRefreshing(false);
        }
        isLoadingMore = false;
    }

    abstract protected void onRefreshData();

    abstract protected void onMoreData();


}
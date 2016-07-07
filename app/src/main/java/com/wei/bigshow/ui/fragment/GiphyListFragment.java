package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;

import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.BadgeItem;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.BadgeItemView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.vandor.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartadapters.SmartAdapter;

public class GiphyListFragment extends BaseRecyclerFragment {

    public static GiphyListFragment instance() {
        GiphyListFragment fragment = new GiphyListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }


    private void initView() {
        GridLayoutManager layoutManager = createGridLayoutManager(2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(BadgeItem.class, BadgeItemView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    /**
     * 这里的返回值是跨度，如果Gridview设置spanCount为2， 则返回2是最长的宽度
     */
    public GridLayoutManager createGridLayoutManager(final int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position%3==0){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        return manager;
    }

    private void initData() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        itemList = new ArrayList<>();
        fetchData();
    }

    @Override
    protected void onRefreshData() {
        isLoadingMore = false;
        fetchData();
    }

    @Override
    protected void onMoreData() {

    }

    private void fetchData() {
        fetchData(false);
    }

    private void fetchData(final boolean isLoadMore) {
        testData(null);

    }

    private void testData(String data) {
        List testList = new ArrayList();
        testList.add(new BadgeItem("巴塞罗那"));
        testList.add(new BadgeItem());
        testList.add(new BadgeItem("巴塞罗那"));
        testList.add(new BadgeItem("利物浦队"));
        testList.add(new BadgeItem("阿森纳队"));
        testList.add(new BadgeItem("广州恒大淘宝队"));
        testList.add(new BadgeItem("阿森纳队"));
        testList.add(new BadgeItem("阿森纳队"));

        itemList = testList;
        handler.obtainMessage(HandlerMsg.MSG_LOAD_LIST_SUC).sendToTarget();
    }

    private void parseData(String data) {

    }

    public interface HandlerMsg {
        int MSG_LOAD_LIST_SUC = 2102;
        int MSG_LOAD_LIST_FAIL = 2008;
        int MSG_LOAD_LIST_EMPTY = 2106;
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case HandlerMsg.MSG_LOAD_LIST_SUC:
                    resetDataLoadLayout();
                    adapter.setItems(itemList);
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    break;

                case HandlerMsg.MSG_LOAD_LIST_EMPTY:
                    resetDataLoadLayout();
                    if (itemList.size() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
//                    ToastUtils.toast(getActivity(), R.string.no_more_data);
                    }
                    break;
                case HandlerMsg.MSG_LOAD_LIST_FAIL:
                    resetDataLoadLayout();
                    if (itemList.size() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                    } else {
//                    ToastUtils.toast(getActivity(), R.string.fail_to_load);
                    }
                    break;
            }
        }
    };

}


package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.core.net.ApiManager;
import com.wei.bigshow.model.CreateEmptyEntity;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.network.GiphyResponse;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.CreateEmptyView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.vandor.MultiStateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CreateListFragment extends BaseRecyclerFragment {

    public static final String TAG = CreateListFragment.class.getSimpleName();

    public static CreateListFragment instance() {
        CreateListFragment fragment = new CreateListFragment();
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
                .map(CreateEmptyEntity.class, CreateEmptyView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void initData() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        itemList = new ArrayList<>();
        testData(null);
        //fetchData();
    }

    @Override
    protected void onRefreshData() {
        isLoadingMore = false;
        //fetchData();
        resetDataLoadLayout();
    }

    @Override
    protected void onMoreData() {

    }

    private void fetchData() {
        fetchData(false);
    }

    private void fetchData(final boolean isLoadMore) {

        Subscriber mSubscriber = new Subscriber<GiphyResponse<List<GiphyEntity>>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "on Completed !");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "on Error !");
            }

            @Override
            public void onNext(GiphyResponse<List<GiphyEntity>> giphyResponse) {
                Log.e(TAG, "on Next !");
                try {
                    if (giphyResponse.data.size() == 0) {
                        Log.e(TAG, "on size 0 !");
                        return;
                    }
                    List<GiphyEntity> list = giphyResponse.data;
                    itemList = list;
                    resetDataLoadLayout();
                    adapter.setItems(itemList);
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

                } catch (Exception e) {
                }
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("rating", "g");
        map.put("limit", "20");

        mSubscription = ApiManager.getInstance().apiService.getTrendingData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .subscribe(mSubscriber);

    }

    private void testData(String data) {
        List testList = new ArrayList();
        testList.add(new CreateEmptyEntity());

        itemList = testList;
        handler.obtainMessage(HandlerMsg.MSG_LOAD_LIST_SUC).sendToTarget();
    }

    private void parseData(String data) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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


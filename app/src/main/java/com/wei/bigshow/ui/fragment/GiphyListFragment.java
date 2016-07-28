package com.wei.bigshow.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wei.bigshow.common.SharedPrefType;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.core.net.ApiManager;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.network.GiphyResponse;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.BadgeItemView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.vandor.MultiStateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.utils.Mapper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GiphyListFragment extends BaseRecyclerFragment {

    public static final String TAG = GiphyListFragment.class.getSimpleName();

    private int offset = 0;

    public static GiphyListFragment instance() {
        GiphyListFragment fragment = new GiphyListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        loadNativeJson();
//        initData();
    }


    private void initView() {
        GridLayoutManager layoutManager = createGridLayoutManager(2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(GiphyEntity.class, BadgeItemView.class)
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
                int TYPE_LOADING_BOTTOM = Mapper.viewTypeFromViewClass(LoadMoreView.class);
                int type = adapter.getItemViewType(position);
                if(type==TYPE_LOADING_BOTTOM){
                    return spanCount;
                }
                if (position % 3 == 0) {
                    return 2;
                } else {
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
        isLoadingMore = true;
        adapter.addItem(loadmoreEntity);
        recyclerView.scrollToPosition(itemList.size());
        fetchData(true);

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
                    if(isLoadMore){
                        itemList.addAll(list);
                    }else{
                        itemList = list;
                    }
                    resetDataLoadLayout();
                    adapter.setItems(itemList);
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

                    saveNativeJson();

                } catch (Exception e) {
                }
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("rating", "g");
        map.put("limit", "10");
        offset = isLoadMore ? ++offset : 0;
        map.put("offset", 10 * offset+"");

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

    public void loadNativeJson(){
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);

        SharedPreferences sp = getContext().getSharedPreferences(SharedPrefType.SP_NAME, Context.MODE_PRIVATE);
        String nativeJson = sp.getString(SharedPrefType.STORE_GIPHY_LIST, "");

        try {
            if (nativeJson == null || nativeJson.equals("")) {
                itemList = new ArrayList();
            } else {
                Gson gson = new Gson();
                List<GiphyEntity> list = gson.fromJson(nativeJson, new TypeToken<List<GiphyEntity>>() {
                }.getType());
                itemList = list;
            }

            resetDataLoadLayout();
            adapter.setItems(itemList);
        } catch (Exception e) {
        }

        if(itemList.size()>0){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }else{
            fetchData();
        }
    }

    private void saveNativeJson(){
        if (itemList.size() <= 0) return;
        List saveList = itemList.subList(0, itemList.size());
        Gson gson = new GsonBuilder().create();
        String saveJson = gson.toJson(saveList);
        SharedPreferences sp = getContext().getSharedPreferences(SharedPrefType.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SharedPrefType.STORE_GIPHY_LIST, saveJson).commit();
    }

}


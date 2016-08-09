package com.wei.bigshow.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wei.bigshow.common.SharedPrefType;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.core.net.ApiManager;
import com.wei.bigshow.model.CreateEmptyEntity;
import com.wei.bigshow.model.NativeStory;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.network.GiphyResponse;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.CreateEmptyView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.adapter.SimpleView;
import com.wei.bigshow.ui.vandor.MultiStateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        loadNativeJson();
        initEvent();
        setupRecycleSwipe();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(CreateEmptyEntity.class, CreateEmptyView.class)
                .map(NativeStory.class, SimpleView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void setupRecycleSwipe() {
        ItemTouchHelper.SimpleCallback mCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.ACTION_STATE_SWIPE) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //viewHolder 中有一切我们想要的数据
                int position = viewHolder.getAdapterPosition();
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void loadNativeJson() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);

        SharedPreferences sp = getContext().getSharedPreferences(SharedPrefType.SP_NAME, Context.MODE_PRIVATE);
        String nativeJson = sp.getString(SharedPrefType.STORE_CREATE_LIST_STORY, "");

        try {
            if (nativeJson == null || nativeJson.equals("")) {
                itemList = new ArrayList();
                itemList.add(new CreateEmptyEntity());
            } else {
                Gson gson = new Gson();
                List<NativeStory> list = gson.fromJson(nativeJson, new TypeToken<List<NativeStory>>() {
                }.getType());
                itemList = list;
                itemList.add(new CreateEmptyEntity());
            }

            resetDataLoadLayout();
            adapter.setItems(itemList);
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } catch (Exception e) {
        }

        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }


    public void initEvent() {
        _subscriptions
                .add(_rxBus.toObserverable().subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof CreateEmptyEntity) {

                            NativeStory nativeStory = new NativeStory();
                            nativeStory.text = new Random().nextInt(100) + "";
                            itemList.add(0, nativeStory);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }));
    }

    @Override
    protected void onRefreshData() {
        isLoadingMore = false;
        //fetchData();
        resetDataLoadLayout();
        //save
        saveNativeJson();
    }

    public void saveNativeJson() {
        if (itemList.size() <= 1) return;
        List saveList = itemList.subList(0, itemList.size() - 1);
        Gson gson = new GsonBuilder().create();
        String saveJson = gson.toJson(saveList);
        SharedPreferences sp = getContext().getSharedPreferences(SharedPrefType.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SharedPrefType.STORE_CREATE_LIST_STORY, saveJson).commit();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}


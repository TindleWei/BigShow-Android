package com.wei.bigshow.ui.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.core.net.ApiManager;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.network.GiphyResponse;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.adapter.GiphyItemView;
import com.wei.bigshow.ui.adapter.LoadMoreView;
import com.wei.bigshow.ui.vandor.MultiStateView;
import com.wei.bigshow.ui.vandor.keyevent.KeyEventUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nlopez.smartadapters.SmartAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Zeus 跳转 图片搜索类
 * created by Adam Wei
 */

public class GiphySearchFragment extends BaseRecyclerFragment {

    private final static String TAG = "GiphySearchFragment";

    private SearchView mSearchView;

    private int offset = 0;

    private boolean isSearching = false;
    private String searchKey = "";

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

    public void initSearchView(final Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            MenuItemCompat.setOnActionExpandListener(
                    searchItem, new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            isSearching = true;
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            isSearching = false;
                            return true;
                        }
                    });
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            mSearchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
            mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_FULLSCREEN);
            mSearchView.setQueryHint(getString(R.string.action_search));
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.trim().equals("")) return true;
                    Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                    searchData(query, false);
                    searchKey = query;
                    KeyEventUtil.hideKeyboard(getActivity());
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.trim().equals("")) return true;
                    //Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_giphy_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        initSearchView(menu);
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
//                .map(PlotMeta.class, PlotMetaView.class)
                .map(GiphyEntity.class, GiphyItemView.class)
                .map(LoadViewEntity.class, LoadMoreView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(true);
        initRefreshLayout();
        initLoadMoreLayout(layoutManager);
    }

    private void initData() {

        fetchData();
    }

    @Override
    protected void onMoreData() {
        isLoadingMore = true;
        adapter.addItem(loadmoreEntity);
        recyclerView.scrollToPosition(itemList.size());

        if(isSearching){
            searchData(searchKey, true);
        }else{
            fetchData(true);
        }
    }

    @Override
    protected void onRefreshData() {
        // do nothing
        isLoadingMore = false;
        if(isSearching){
            searchData(searchKey, false);
        }else{
            fetchData();
        }

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

                } catch (Exception e) {
                }
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("rating", "g");
        map.put("limit", "5");
        offset = isLoadMore ? ++offset : 0;
        map.put("offset", 5 * offset+"");

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

    private void searchData(String searchKey, final boolean isLoadMore) {

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

                } catch (Exception e) {
                }
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("q", searchKey);
        map.put("rating", "g");
        map.put("limit", "5");
        offset = isLoadMore ? ++offset : 0;
        map.put("offset", 5 * offset+"");

        mSubscription = ApiManager.getInstance().apiService.getSearchData(map)
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
}

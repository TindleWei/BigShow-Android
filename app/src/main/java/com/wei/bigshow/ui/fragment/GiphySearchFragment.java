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

public class GiphySearchFragment extends BaseRecyclerFragment {

    private SearchView mSearchView;

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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            MenuItemCompat.setOnActionExpandListener(
                    searchItem, new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            return true;
                        }
                    });
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            mSearchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
            mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
            mSearchView.setQueryHint(getString(R.string.action_search));
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return onQueryTextChange(query);
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
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

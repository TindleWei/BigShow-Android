package com.wei.bigshow.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wei.bigshow.R;
import com.wei.bigshow.model.view.LoadViewEntity;
import com.wei.bigshow.ui.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午3:28
 */
public abstract class BaseListFragment extends BaseFragment implements ViewEventListener<Object> {

    protected RecyclerMultiAdapter adapter;

//    @Bind(R.id.multi_stateview)
//    protected MultiStateView multiStateView;

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected List itemList = new ArrayList();
    protected LoadViewEntity loadmoreEntity;
    protected boolean isLoadingMore = false;

    abstract protected @LayoutRes int getLayoutResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewEvent(int actionId, Object topic, int position, View view) {

    }

    public void initRefreshLayout(){
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

    public void initLoadMoreLayout(LinearLayoutManager layoutmanager){
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

    abstract protected void onRefreshData();

    abstract protected void onMoreData();

    public void initActionbar(String title) {
        RelativeLayout parent_layout = (RelativeLayout) getView().findViewById(R.id.action_bar_color_parent);
        if (title == null)
            parent_layout.setVisibility(View.GONE);
        ((TextView) getView().findViewById(R.id.actionbar_tv_title)).setText(title);
        getView().findViewById(R.id.actionbar_layout_left).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).onBackPressed();
            }
        });
    }
}
package com.gamebuddy.bigshow.common.base;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.model.view.LoadViewEntity;
import com.gamebuddy.bigshow.view.listener.EndlessRecyclerOnScrollListener;

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
public abstract class BaseListActivity extends BaseActivity implements ViewEventListener<Object> {

    protected RecyclerMultiAdapter adapter;

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected List itemList = new ArrayList();
    protected LoadViewEntity loadmoreEntity;
    protected boolean isLoadingMore = false;

    @Override
    public void onViewEvent(int actionId, Object topic, int position, View view) {

    }

    public void initActionbar(String title) {
        RelativeLayout parent_layout = (RelativeLayout) findViewById(R.id.action_bar_color_parent);
        if (title == null)
            parent_layout.setVisibility(View.GONE);
        ((TextView)findViewById(R.id.actionbar_tv_title)).setText(title);
        findViewById(R.id.actionbar_layout_left).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(Color.RED);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    abstract protected void onRefreshData();

    abstract protected void onMoreData();
}
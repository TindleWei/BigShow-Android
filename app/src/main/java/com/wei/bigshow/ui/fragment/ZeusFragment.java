package com.wei.bigshow.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseRecyclerFragment;
import com.wei.bigshow.model.story.PlotMeta;
import com.wei.bigshow.model.story.PlotOptions;
import com.wei.bigshow.model.zeus.GuideHeaderItem;
import com.wei.bigshow.rx.event.GiphyTapEvent;
import com.wei.bigshow.ui.adapter.zeus.GuideHeaderView;
import com.wei.bigshow.ui.adapter.zeus.PlotMetaView;
import com.wei.bigshow.ui.adapter.zeus.PlotOptionsView;

import java.util.ArrayList;

import io.nlopez.smartadapters.SmartAdapter;
import rx.functions.Action1;

/**
 * 具有上帝视角的故事编辑类
 * created by Adam Wei
 */
public class ZeusFragment extends BaseRecyclerFragment{

    public static ZeusFragment instance(Bundle bundle) {
        ZeusFragment  fragment = new ZeusFragment();
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

        initToolbar();
        initView();
        initData();
        initEvent();
    }

    public void initToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = SmartAdapter.empty()
                .map(GuideHeaderItem.class, GuideHeaderView.class)
                .map(PlotMeta.class, PlotMetaView.class)
                .map(PlotOptions.class, PlotOptionsView.class)
                .into(recyclerView);
        swipeRefreshLayout.setEnabled(false);
    }

    private void initData() {
        itemList = new ArrayList<>();
        itemList.add(new GuideHeaderItem());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        itemList.add(new PlotMeta());
        itemList.add(new PlotOptions());
        adapter.setItems(itemList);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_zeus_save, menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_zeus_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_save) {
            Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onMoreData() {
        // do nothing
    }

    @Override
    protected void onRefreshData() {
        // do nothing
    }

    public PlotMeta editPlotMeta = null;

    public void initEvent() {

        _subscriptions
                .add(_rxBus.toObserverable().subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof GiphyTapEvent) {
                            // here we come
                            editPlotMeta.src = ((GiphyTapEvent) event).url;
                            editPlotMeta.text = ((GiphyTapEvent) event).slug;
                            itemList.remove(editPlotMeta.pos);
                            itemList.add(editPlotMeta.pos, editPlotMeta);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), ((GiphyTapEvent) event).url, Toast.LENGTH_SHORT).show();
                        } else if (event instanceof PlotMeta) {
                            editPlotMeta = ((PlotMeta) event);
                        }
                    }
                }));
    }
}

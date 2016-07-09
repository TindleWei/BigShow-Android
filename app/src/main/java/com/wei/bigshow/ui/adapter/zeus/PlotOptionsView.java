package com.wei.bigshow.ui.adapter.zeus;

import android.content.Context;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.story.PlotOptions;

import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 16/7/8 下午11:19
 */
public class PlotOptionsView extends BaseAdapterItemView<PlotOptions> {

    public PlotOptionsView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_plot_options;
    }

    /**
     * 在layout加载完后执行, 进行View的绑定
     */
    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    /**
     * bind(item, position) 在bind(item）之前发生
     */
    @Override
    public void bind(PlotOptions item, int position) {
        super.bind(item, position);
    }

    @Override
    public void bind(final PlotOptions item) {


    }
}
package com.wei.bigshow.ui.adapter;

import android.content.Context;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.BadgeItem;

import butterknife.ButterKnife;

/**
 * describe
 * created by weizepeng
 * created time 16/6/30 下午5:45
 */
public class BadgeItemView extends BaseAdapterItemView<BadgeItem> {


    public BadgeItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_badge_view;
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
    public void bind(BadgeItem item, int position) {
        super.bind(item, position);
    }

    @Override
    public void bind(final BadgeItem item) {


    }
}

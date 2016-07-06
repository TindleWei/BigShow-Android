package com.wei.bigshow.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableFrameLayout;

public abstract class BaseAdapterItemView<T> extends BindableFrameLayout<T> {

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    public BaseAdapterItemView(Context context) {
        super(context);
        init();
    }

    public BaseAdapterItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseAdapterItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BaseAdapterItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mDensity = metrics.density;

    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    /**
     * findViewById简化
     * 在ButterKnife失效的状况下
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(View v, int id) {
        return (T) v.findViewById(id);
    }

    /**
     * findViewById简化
     * 在ButterKnife失效的状况下
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) getRootView().findViewById(id);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        return (int) (dpValue * mDensity + 0.5f);
    }
}
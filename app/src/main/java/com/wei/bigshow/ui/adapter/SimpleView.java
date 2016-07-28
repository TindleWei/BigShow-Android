package com.wei.bigshow.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.NativeStory;
import com.wei.bigshow.ui.activity.SimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * describe
 * created by weizepeng
 * created time 16/6/30 下午5:45
 */
public class SimpleView extends BaseAdapterItemView<Object> {


    @Bind(R.id.tv_text)
    TextView tvText;

    public SimpleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_create_empty_view;
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
    public void bind(Object item, int position) {
        super.bind(item, position);

    }

    @Override
    public void bind(final Object item) {
        tvText.setText(item.getClass().getSimpleName());
        if(item instanceof NativeStory){
            tvText.setText(((NativeStory)item).text);

            initEvent();
        }
    }

    private void initEvent(){
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SimpleActivity.FRAGMENT_TYPE, SimpleActivity.ZEUS);
                SimpleActivity.start(getContext(), bundle);
            }
        });

    }
}

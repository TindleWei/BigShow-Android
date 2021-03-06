package com.wei.bigshow.ui.adapter.zeus;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.zeus.GuideHeaderItem;
import com.wei.bigshow.ui.activity.GuideDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 16/7/8 下午11:18
 */
public class GuideHeaderView extends BaseAdapterItemView<GuideHeaderItem> {

    @Bind(R.id.textView)
    TextView textView;

    public GuideHeaderView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_guide_header;
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
    public void bind(GuideHeaderItem item, int position) {
        super.bind(item, position);
    }

    @Override
    public void bind(final GuideHeaderItem item) {
        textView.setSelected(true);//For marquee

        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GuideDetailActivity.start(v.getContext());
            }
        });
    }
}

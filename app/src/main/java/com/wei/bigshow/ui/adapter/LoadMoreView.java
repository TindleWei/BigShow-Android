package com.wei.bigshow.ui.adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.view.LoadViewEntity;

import butterknife.Bind;


/**
 * describe
 * created by tindle
 * created time 16/3/23 上午11:29
 */
public class LoadMoreView extends BaseAdapterItemView<LoadViewEntity> {

    @Bind(R.id.load_progress_bar)
    ProgressBar loadProgressBar;

    @Bind(R.id.footer_layout)
    FrameLayout footerLayout;

    public LoadMoreView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_load_more;
    }

    @Override
    public void bind(LoadViewEntity item, int position) {
        super.bind(item, position);
    }

    @Override
    public void bind(final LoadViewEntity item) {
        //image scale 16:9
        FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) loadProgressBar.getLayoutParams();
        lp2.width = mScreenWidth;
    }
}

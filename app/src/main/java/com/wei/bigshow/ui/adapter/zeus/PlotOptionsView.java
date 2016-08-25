package com.wei.bigshow.ui.adapter.zeus;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.zeus.PlotOptionItem;
import com.wei.bigshow.ui.fragment.EditItemDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 16/7/8 下午11:19
 */
public class PlotOptionsView extends BaseAdapterItemView<PlotOptionItem> {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

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
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        viewPager.getLayoutParams().width = displayMetrics.widthPixels;
        viewPager.requestLayout();
    }

    /**
     * bind(item, position) 在bind(item）之前发生
     */
    @Override
    public void bind(PlotOptionItem item, int position) {
        super.bind(item, position);
    }

    @Override
    public void bind(final PlotOptionItem item) {

        setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditItemDialog.newInstance("hi", position).show(((FragmentActivity)getContext()).getFragmentManager(), EditItemDialog.TAG);
            }
        });

        viewPager.setCurrentItem(item.isEnd?1:0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            int prePosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == prePosition) return;
                switch (position){
                    case 0:
                        item.isEnd = false;
                        break;
                    case 1:
                        item.isEnd = true;
                        break;
                }
                prePosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {

        public Object instantiateItem(ViewGroup collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.first_layout;
                    break;
                case 1:
                    resId = R.id.second_layout;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }
    }
}
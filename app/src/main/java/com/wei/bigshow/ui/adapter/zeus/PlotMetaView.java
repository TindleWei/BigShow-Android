package com.wei.bigshow.ui.adapter.zeus;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.story.PlotMeta;
import com.wei.bigshow.rx.RxBus;
import com.wei.bigshow.ui.activity.SimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 16/7/8 下午11:18
 */
public class PlotMetaView extends BaseAdapterItemView<PlotMeta> {

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;

    public PlotMetaView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_plot_meta;
    }

    /**
     * 在layout加载完后执行, 进行View的绑定
     */
    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(PlotMeta item, final int position) {
        super.bind(item, position);

        if (item.src != null) {
            Glide.with(getContext())
                    .load(item.src)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.image_bg)
                    .dontAnimate()
                    .into(imageView);
        }else{
            Glide.with(getContext())
                    .load(R.mipmap.image)
                    .placeholder(R.drawable.image_bg)
                    .into(imageView);
        }

        if(item.text!=null){
            textView.setText(item.text);
        }else{
            textView.setText("gif");
        }


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new PlotMeta(position));

                Bundle bundle = new Bundle();
                bundle.putString(SimpleActivity.FRAGMENT_TYPE, SimpleActivity.GIPHY_SEARCH);
                SimpleActivity.start(v.getContext(), bundle);
            }
        });
    }

    @Override
    public void bind(PlotMeta plotMeta) {

    }
}

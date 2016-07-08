package com.wei.bigshow.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseAdapterItemView;
import com.wei.bigshow.model.network.GiphyEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * describe
 * created by weizepeng
 * created time 16/6/30 下午5:45
 */
public class BadgeItemView extends BaseAdapterItemView<GiphyEntity> {


    @Bind(R.id.imageView)
    ImageView imageView;

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
    public void bind(GiphyEntity item, int position) {
        super.bind(item, position);

        String url_width_gif = item.images.fixed_width.url;
        String url_height_gif = item.images.fixed_height_small.url;
        String url_still_width = item.images.fixed_width_still.url;
        String url_still_height = item.images.fixed_height_still.url;

        int width = Integer.valueOf(item.images.fixed_width.width);
        int height = Integer.valueOf(item.images.fixed_width.height);

        if (position % 3 == 0) {

//            imageView.getLayoutParams().height = (int) (mScreenWidth * 1.0 * height / width);

            Glide.with(getContext())
                    .load(url_width_gif)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.image_bg)
                    .dontAnimate()
                    .into(imageView);
        } else {

//            imageView.getLayoutParams().height = (int) (mScreenWidth * 1.0 * height / width);

            Glide.with(getContext())
                    .load(url_height_gif)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.image_bg)
                    .dontAnimate()
                    .into(imageView);
        }
    }

    @Override
    public void bind(final GiphyEntity item) {


    }
}

package com.gamebuddy.bigshow.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseAdapterItemView;
import com.gamebuddy.bigshow.common.event.GridPhotoEvent;
import com.gamebuddy.bigshow.model.network.GiphyEntity;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * describe
 * created by tindle
 * created time 16/4/15 下午1:57
 */
public class SearchItemView extends BaseAdapterItemView<GiphyEntity>{

    @Bind(R.id.iv_cover)
    ImageView iv_cover;

    public SearchItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search_grid;
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
    }

    @Override
    public void bind(GiphyEntity item, int position) {
        super.bind(item, position);
        this.setBackgroundColor(Color.GRAY);
//        if(position%3==0){
//            this.setBackgroundColor(Color.RED);
//        }else if(position%3==1){
//            this.setBackgroundColor(Color.YELLOW);
//        }else{
//            this.setBackgroundColor(Color.GREEN);
//        }
    }

    @Override
    public void bind(final GiphyEntity item) {
        try{
            String url = item.images.fixed_width.url;
            int width = Integer.valueOf(item.images.fixed_height.width);
            int height = Integer.valueOf(item.images.fixed_height.height);
            Glide.with(getContext())
                    .load(url)
                    .asGif()
                    .placeholder(R.drawable.image_bg)
                    .centerCrop()
                    .crossFade()
                    .into(iv_cover);
        }catch (Exception e){
            Log.e("TAG","Bind NO");
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //封装数据
                String saveUrl = item.images.fixed_width.url;
                EventBus.getDefault().post(new GridPhotoEvent(saveUrl));
                ((Activity)getContext()).finish();
            }
        });
    }
}

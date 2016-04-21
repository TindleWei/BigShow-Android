package com.gamebuddy.bigshow.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseAdapterItemView;
import com.gamebuddy.bigshow.model.network.GiphyEntity;

import butterknife.Bind;

/**
 * describe
 * created by tindle
 * created time 16/4/15 下午1:57
 */
public class PlotGridRootView extends BaseAdapterItemView<GiphyEntity>{

    @Bind(R.id.iv_cover)
    ImageView iv_cover;

    @Bind(R.id.tv_content)
    TextView tv_content;

    public PlotGridRootView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_plot_grid_first;
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
    }

    @Override
    public void bind(GiphyEntity item, int position) {
        super.bind(item, position);
        tv_content = (TextView)findViewById(R.id.tv_content);
        tv_content.setText(position+"");
    }

    @Override
    public void bind(final GiphyEntity item) {
//        try{
//            String url = item.images.fixed_width.url;
//            int width = Integer.valueOf(item.images.fixed_height.width);
//            int height = Integer.valueOf(item.images.fixed_height.height);
//            Glide.with(getContext())
//                    .load(url)
//                    .asGif()
//                    .placeholder(R.drawable.image_bg)
//                    .centerCrop()
//                    .crossFade()
//                    .into(iv_cover);
//        }catch (Exception e){
//            Log.e("TAG","Bind NO");
//        }
//
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //封装数据
//                String saveUrl = item.images.fixed_width.url;
//                EventBus.getDefault().post(new GridPhotoEvent(saveUrl));
//                ((Activity)getContext()).finish();
//            }
//        });
    }
}

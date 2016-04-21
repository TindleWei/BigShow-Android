package com.gamebuddy.bigshow.view.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseAdapterItemView;
import com.gamebuddy.bigshow.model.network.GiphyEntity;
import com.gamebuddy.bigshow.view.activity.PlotMakerActivity;

import butterknife.Bind;

/**
 * describe
 * created by tindle
 * created time 16/4/15 下午1:57
 */
public class PlotGridSubView extends BaseAdapterItemView<GiphyEntity>{

    @Bind(R.id.iv_cover)
    ImageView iv_cover;

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.layout_cardview)
    LinearLayout layout_cardview;

    public PlotGridSubView(Context context) {
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

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //封装数据
                Intent intent = new Intent(getContext(), PlotMakerActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (AppCompatActivity)getContext(), Pair.create(findViewById(R.id.layout_cardview), "transition_element"));
                ActivityCompat.startActivity((AppCompatActivity)getContext(), intent, options.toBundle());
            }
        });
    }
}

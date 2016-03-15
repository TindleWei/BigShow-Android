package com.gamebuddy.bigshow.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseActivity;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午1:19
 */
public class PlotMakerActivity extends BaseActivity{

    private ExitActivityTransition exitTransition;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sub;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);

        exitTransition = ActivityTransition.with(getIntent()).duration(500).to(findViewById(R.id.sub_imageView)).start(savedInstanceState);

        String url = getIntent().getStringExtra("image_url");
        if(!url.equals("")){
            ImageView sub_imageView = (ImageView)findViewById(R.id.sub_imageView);
            Glide.with(mContext)
                    .load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.image_bg)
                    .centerCrop()
                    .crossFade()
                    .into(sub_imageView);
        }
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }
}

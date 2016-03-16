package com.gamebuddy.bigshow.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseActivity;
import com.gamebuddy.bigshow.view.view.VideoMediumItemView;
import com.kennyc.view.MultiStateView;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午1:19
 */
public class PlotMakerActivity extends BaseActivity implements ViewEventListener<Object> {

    RecyclerMultiAdapter adapter;

    @Bind(R.id.multiStateView)
    MultiStateView multiStateView;

    @Bind(R.id.recycler_view)
    RecyclerView topicListView;

    @Bind(R.id.iv_last_cover)
    ImageView iv_last_cover;

    List<Object> currentItems = new ArrayList<>();

    private ExitActivityTransition exitTransition;

    public ExitActivityTransition getTransition() {
        return exitTransition;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_plot_maker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("image_url");
        if (!url.equals("")) {
            ImageView sub_imageView = (ImageView) findViewById(R.id.sub_imageView);
            Glide.with(mContext)
                    .load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.image_bg)
                    .centerCrop()
                    .crossFade()
                    .into(iv_last_cover);
        }

        exitTransition = ActivityTransition.with(getIntent()).duration(500).to(findViewById(R.id.iv_last_cover)).start(savedInstanceState);

        topicListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = SmartAdapter.empty()
                .map(Object.class, VideoMediumItemView.class)
//                .map(Object.class, VideoLargeItemView.class)
                .listener(this)
                .into(topicListView);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        lazyLoad();


    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }

    @Override
    public void onViewEvent(int actionId, Object obj, int position, View view) {

    }

    public void lazyLoad() {

    }
}

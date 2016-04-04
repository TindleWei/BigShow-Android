package com.gamebuddy.bigshow.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseActivity;
import com.gamebuddy.bigshow.common.event.TransitionEvent;
import com.gamebuddy.bigshow.presenter.intent.GifData;
import com.gamebuddy.bigshow.view.view.VideoMediumItemView;
import com.kennyc.view.MultiStateView;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午1:19
 */
public class PlotMakerActivity extends BaseActivity implements ViewEventListener<Object> {

    private static final String TAG = "PlotMakerActivity";

    RecyclerMultiAdapter adapter;

    @Bind(R.id.multiStateView)
    MultiStateView multiStateView;

    @Bind(R.id.recycler_view)
    RecyclerView topicListView;

    @Bind(R.id.iv_last_cover)
    ImageView iv_last_cover;

    @Bind(R.id.layout_cardview)
    CardView layout_cardview;

    @Bind(R.id.layout_inner_card)
    LinearLayout layout_inner_card;

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

        layout_cardview.setAlpha(0);
        layout_cardview.animate().setDuration(500).setStartDelay(500).alpha(1).start();


        GifData gifData = (GifData) getIntent().getSerializableExtra("data");

        if (gifData != null) {
            String url = gifData.url;
            int width = gifData.width;
            int height = gifData.height;


            float mDensity = getBaseContext().getResources().getDisplayMetrics().density;
            float mScreenWidth = getBaseContext().getResources().getDisplayMetrics().widthPixels;
            int picConstantWidth = (int) (mScreenWidth - 2 * 20 * mDensity);
            int picConstantHeight = (int) (mScreenWidth * 9 / 16);

            ViewGroup.LayoutParams lp = iv_last_cover.getLayoutParams();
            lp.width = picConstantWidth;
            lp.height = picConstantHeight;

            FrameLayout.LayoutParams lp_layout_inner_card = (FrameLayout.LayoutParams) layout_inner_card.getLayoutParams();
            lp_layout_inner_card.setMargins(0, picConstantHeight, 0, 0);

            if (!url.equals("")) {
                Glide.with(mContext)
                        .load(url)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.image_bg)
                        .centerCrop()
                        .crossFade()
                        .into(iv_last_cover);
            }
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
        layout_cardview.animate().setDuration(300).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                exitTransition.exit(PlotMakerActivity.this);
                EventBus.getDefault().post(new TransitionEvent());

            }
        });

    }

    @Override
    public void onViewEvent(int actionId, Object obj, int position, View view) {

    }

    public void lazyLoad() {

    }
}

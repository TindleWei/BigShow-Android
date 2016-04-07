package com.gamebuddy.bigshow.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseActivity;
import com.gamebuddy.bigshow.common.event.TransitionEvent;
import com.gamebuddy.bigshow.presenter.intent.GifData;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午1:19
 */
public class PlotMakerActivity extends BaseActivity implements ViewEventListener<Object> {

    private static final String TAG = "PlotMakerActivity";

    public static final int RESULT_PLOT_CONTENT = 1;
    public static final int RESULT_PLOT_CHOOSE_1 = 2;
    public static final int RESULT_PLOT_CHOOSE_2 = 3;
    public static final int RESULT_CANCEL = 0;

    @Bind(R.id.iv_last_cover)
    ImageView iv_last_cover;

    @Bind(R.id.layout_cardview)
    CardView layout_cardview;

    @Bind(R.id.layout_inner_card)
    LinearLayout layout_inner_card;

    @Bind(R.id.tv_plot_content)
    TextView tv_plot_content;

    @Bind(R.id.tg_is_end)
    ToggleButton tg_is_end;

    @Bind(R.id.tv_choosen_1)
    TextView tv_choosen_1;

    @Bind(R.id.tv_choosen_2)
    TextView tv_choosen_2;

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

        tv_plot_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                int width = v.getWidth();
                int height = v.getHeight();
                Log.e("Tag", "x: " + x + "\ny: " + y + "\nwidth: " + width + "\nheight: " + height);
                Log.d("test", "left:" + v.getLeft());
                Log.d("test", "right:" + v.getRight());
                Log.d("test", "Top:" + v.getTop());
                Log.d("test", "Bottom:" + v.getBottom());

                Intent intent = new Intent(mContext, DialogActivity.class);
                intent.putExtra("type", RESULT_PLOT_CONTENT);
                intent.putExtra("title", "修改故事内容");
                intent.putExtra("content", tv_plot_content.getText().toString());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, tv_plot_content,
                        getString(R.string.transition_dialog));
                startActivityForResult(intent, 0, options.toBundle());

            }
        });

        tg_is_end.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //close option btns

                } else {
                    //show option btns
                }
            }
        });

        tv_choosen_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DialogActivity.class);
                intent.putExtra("title", "修改A选项");
                intent.putExtra("type", RESULT_PLOT_CHOOSE_1);
                intent.putExtra("content", tv_choosen_1.getText().toString());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, tv_choosen_1,
                        getString(R.string.transition_dialog));
                startActivityForResult(intent, 0, options.toBundle());
            }
        });

        tv_choosen_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DialogActivity.class);
                intent.putExtra("type", RESULT_PLOT_CHOOSE_2);
                intent.putExtra("title", "修改选项");
                intent.putExtra("content", tv_choosen_2.getText().toString());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, tv_choosen_2,
                        getString(R.string.transition_dialog));
                startActivityForResult(intent, 0, options.toBundle());
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null) return;
        switch (resultCode){
            case RESULT_PLOT_CONTENT:
                tv_plot_content.setText(data.getStringExtra("result"));
                break;
            case RESULT_PLOT_CHOOSE_1:
                tv_choosen_1.setText(data.getStringExtra("result"));
                break;
            case RESULT_PLOT_CHOOSE_2:
                tv_choosen_2.setText(data.getStringExtra("result"));
                break;
        }
    }
}

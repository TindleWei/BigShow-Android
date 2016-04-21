package com.gamebuddy.bigshow.view.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
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
import com.gamebuddy.bigshow.common.event.GridPhotoEvent;
import com.gamebuddy.bigshow.presenter.intent.GifData;
import com.gamebuddy.bigshow.view.vandor.curl.CurlPage;
import com.gamebuddy.bigshow.view.vandor.curl.CurlView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
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

    @Bind(R.id.curl)
    CurlView mCurlView;

    @Bind(R.id.layout_cover_option)
    LinearLayout layout_cover_option;

    @Bind(R.id.tv_save)
    TextView tv_save;

    List<Object> currentItems = new ArrayList<>();

    GifData gifData;

    int picConstantWidth;
    int picConstantHeight;

    Bitmap b = null;

    // 是否故事的第一话
    boolean isStoryHead = false;

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

//        exitTransition = ActivityTransition.with(getIntent()).duration(500).to(findViewById(R.id.iv_last_cover)).start(savedInstanceState);

        initData();
        initEvent();

        new Thread() {
            @Override
            public void run() {
                try {
                    b = Glide.
                            with(mContext).
                            load(gifData.url).
                            asBitmap().
                            into(picConstantWidth, picConstantHeight). // Width and height
                            get();
                    if (b != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initCurlView();
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public void initData() {
//        layout_cardview.setAlpha(0);
//        layout_cardview.animate().setDuration(500).setStartDelay(500).alpha(1).start();

        gifData = (GifData) getIntent().getSerializableExtra("data");

        if (gifData != null) {
            String url = gifData.url;
            int width = gifData.width;
            int height = gifData.height;

            float mDensity = getBaseContext().getResources().getDisplayMetrics().density;
            float mScreenWidth = getBaseContext().getResources().getDisplayMetrics().widthPixels;
            picConstantWidth = (int) (mScreenWidth - 2 * 20 * mDensity);
            picConstantHeight = (int) (mScreenWidth * 9 / 16);

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
    }

    public void initEvent() {
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

        tv_choosen_2.setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btn_cover_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchGridActivity.class));
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlotGridActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        PlotMakerActivity.this, Pair.create(findViewById(R.id.layout_outer), "transition_element"));
                ActivityCompat.startActivity(PlotMakerActivity.this, intent, options.toBundle());
            }
        });
    }


    @Override
    public void onBackPressed() {
//        layout_cardview.animate().setDuration(300).alpha(0).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                exitTransition.exit(PlotMakerActivity.this);
//                EventBus.getDefault().post(new TransitionEvent());
//
//            }
//        });
        super.onBackPressed();

    }

    @Override
    public void onViewEvent(int actionId, Object obj, int position, View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        switch (resultCode) {
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

    public void initCurlView() {
        mCurlView = (CurlView) findViewById(R.id.curl);
        ViewGroup.LayoutParams lp = mCurlView.getLayoutParams();
        lp.width = picConstantWidth;
        lp.height = picConstantHeight;
        mCurlView.setPageProvider(new PageProvider());
        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
        mCurlView.setCurrentIndex(0);
        mCurlView.setBackgroundColor(0xFFFFFFFF);
        mCurlView.setPageCurlListener(new CurlView.PageCurlListener() {
            @Override
            public void onCurlStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_last_cover.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onPageUp() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout_cover_option.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onPageDown() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_last_cover.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurlView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurlView.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mCurlView.getHolder().getSurface().release();
    }

    /**
     * Bitmap provider.
     */
    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
        private int[] mBitmapIds = {R.drawable.world};// R.drawable.obama, R.drawable.road_rage,R.drawable.taipei_101,

        @Override
        public int getPageCount() {
            return 1;
        }

        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            Drawable d = getResources().getDrawable(mBitmapIds[index]);

            int margin = 7;
            int border = 3;
            Rect r = new Rect(margin, margin, width - margin, height - margin);

            int imageWidth = r.width() - (border * 2);
            int imageHeight = imageWidth * d.getIntrinsicHeight()
                    / d.getIntrinsicWidth();
            if (imageHeight > r.height() - (border * 2)) {
                imageHeight = r.height() - (border * 2);
                imageWidth = imageHeight * d.getIntrinsicWidth()
                        / d.getIntrinsicHeight();
            }

            r.left += ((r.width() - imageWidth) / 2) - border;
            r.right = r.left + imageWidth + border + border;
            r.top += ((r.height() - imageHeight) / 2) - border;
            r.bottom = r.top + imageHeight + border + border;

            Paint p = new Paint();
            p.setColor(0xFFFF00FF);
            c.drawRect(r, p);
            r.left += border;
            r.right -= border;
            r.top += border;
            r.bottom -= border;

            d.setBounds(r);
            d.draw(c);

//            if (b != null) {
//                return b;
//            }
            return b;
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {

            switch (index) {
                // First case is image on front side, solid colored back.
                case 0: {
                    Bitmap front = loadBitmap(width, height, 0);
                    page.setTexture(front, CurlPage.SIDE_BOTH);
                    page.setColor(Color.rgb(200, 200, 200), CurlPage.SIDE_BACK);
                    break;
                }
            }
        }
    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//                mCurlView.setMargins(.1f, .05f, .1f, .05f);
                mCurlView.setMargins(.0f, .0f, .0f, .0f);
            } else {
                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//                mCurlView.setMargins(.1f, .1f, .1f, .1f);
                mCurlView.setMargins(.0f, .00f, .0f, .00f);
            }
        }
    }

    public void onEvent(GridPhotoEvent event) {
        String url = event.photoUrl;
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

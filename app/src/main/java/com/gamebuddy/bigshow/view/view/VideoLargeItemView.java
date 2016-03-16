package com.gamebuddy.bigshow.view.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.model.VideoEntity;

import butterknife.Bind;
import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * describe
 * created by tindle
 * created time 15/12/14 下午7:17
 */
public class VideoLargeItemView extends BindableFrameLayout<VideoEntity> {

    @Bind(R.id.tv_title)
    TextView titleView;

    @Bind(R.id.iv_cover)
    ImageView coverView;

    @Bind(R.id.tv_time)
    TextView timeView;

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    public VideoLargeItemView(Context context) {
        super(context);
        getScreenSize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_video_large;
    }

    @Override
    public void bind(final VideoEntity entity) {

        titleView = (TextView) findViewById(R.id.tv_title);
        coverView = (ImageView) findViewById(R.id.iv_cover);
        timeView = (TextView) findViewById(R.id.tv_time);

        //image scale 16:9
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) coverView.getLayoutParams();
        double imageWidth = (mScreenWidth - 2 * dp2px(getContext(), 10));
        lp2.height = (int) (9.0 / 16.0 * imageWidth);

//        final HashMap<String, Object> snippetMap = (HashMap) entity.snippet;
//        HashMap<String, Object> contentDetailsMap = (HashMap) entity.contentDetails;
//
//        titleView.setText((String) snippetMap.get("title"));
//
//        HashMap<String, Object> snippetThumbnailsMap = (HashMap) snippetMap.get("thumbnails");
//        String coverUrl = (String) (((HashMap) snippetThumbnailsMap.get("medium")).get("url"));
//
//        try {
//            Glide.with(getContext())
//                    .load(coverUrl)
//                    .placeholder(R.mipmap.default_load_holder)
//                    .into(coverView);
//        } catch (Exception e) {
//        }

    }

    /**
     * 获取屏幕的宽高密度
     */
    public void getScreenSize() {
        if (mScreenHeight != 0) return;

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

package com.gamebuddy.bigshow.view.view;

import android.content.Context;
import android.widget.ImageView;
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
public class VideoMediumItemView extends BindableFrameLayout<VideoEntity> {

    @Bind(R.id.tv_title)
    TextView titleView;

    @Bind(R.id.iv_cover)
    ImageView coverView;

    @Bind(R.id.tv_time)
    TextView timeView;

    @Bind(R.id.tv_date)
    TextView dateView;

    public VideoMediumItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_video_medium;
    }

    @Override
    public void bind(final VideoEntity entity) {

        titleView = (TextView)findViewById(R.id.tv_title);
        coverView = (ImageView)findViewById(R.id.iv_cover);
        timeView = (TextView)findViewById(R.id.tv_time);
        dateView = (TextView)findViewById(R.id.tv_date);

    }

}

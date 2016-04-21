package com.gamebuddy.bigshow.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * describe
 * created by tindle
 * created time 16/4/20 下午2:38
 */
public class PlotLinesView extends LinearLayout {

    private Paint mPaint;
    private Path mPath;
    private Context mContext;
    private float mLines[][];
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    public PlotLinesView(Context context) {
        super(context);
        init(context);
    }

    public PlotLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlotLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.mContext = context;

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mDensity = metrics.density;

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(dp2px(6));

    }


    public void refreshLines(float lines[][]) {
        Log.e("PlotLinesView", "run here");
        this.mLines = lines;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PlotLinesView", "run here2");
        for (float[] line : mLines) {
            Log.e("PlotLinesView", "run here3");
            if(line!=null){
                canvas.drawLine(line[0], line[1], line[2], line[3], mPaint);
                //mPath.moveTo(line[0], line[1]);
                //mPath.quadTo((line[0]+line[2])/2,(line[1] + line[3]) /2, line[2], line[3]);
                //canvas.drawPath(mPath, mPaint);
            }
        }
    }

    public int dp2px(float dpValue) {
        return (int) (dpValue * mDensity + 0.5f);
    }
}

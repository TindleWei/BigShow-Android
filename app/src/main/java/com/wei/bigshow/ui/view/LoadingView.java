package com.wei.bigshow.ui.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wei.bigshow.R;

/**
 * describe
 * created by tindle
 * created time 16/4/11 上午12:01
 */
public class LoadingView {

    View loadview;
    ProgressBar progressBar;
    TextView textView;
    public Activity mActivity;

    public LoadingView(Activity activity){
        this.mActivity = activity;
    }
    public void init(){
        ViewGroup decor = (ViewGroup) mActivity.getWindow().getDecorView();
        ViewGroup contentParent = (ViewGroup)mActivity.findViewById(android.R.id.content);
        View rootView = contentParent.getChildAt(0);

        if(loadview==null){
            loadview = LayoutInflater.from(mActivity).inflate(R.layout.layout_loadindview,null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            loadview.setLayoutParams(lp);
            contentParent.addView(loadview);
            loadview.setVisibility(View.GONE);
        }
    }

    public void show(){
        loadview.setVisibility(View.VISIBLE);
    }
    public void hide(){
        loadview.setVisibility(View.GONE);
    }


}

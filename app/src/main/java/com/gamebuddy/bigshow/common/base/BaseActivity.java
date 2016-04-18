package com.gamebuddy.bigshow.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.github.pwittchen.prefser.library.Prefser;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * describe
 * created by tindle
 * created time 15/12/9 下午3:23
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    public Prefser prefser;
    protected Subscription mSubscription;

    protected String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        prefser = new Prefser(this);
        mSubscription = Subscriptions.empty();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    abstract protected
    @LayoutRes
    int getLayoutResId();

    /**
     * default eventbus methos
     */
    public void onEvent(Object object) {

    }
}

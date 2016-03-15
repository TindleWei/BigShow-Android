package com.gamebuddy.bigshow.common.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.pwittchen.prefser.library.Prefser;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午3:28
 */
public abstract class BaseFragment extends Fragment {

    public Prefser prefser;

    protected Subscription mSubscription = Subscriptions.empty();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        prefser = new Prefser(getContext());
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);

    }

    protected String getTitle() {
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
//        FlurryAgent.onPageView();
    }
}

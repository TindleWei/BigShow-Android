package com.gamebuddy.bigshow.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.prefser.library.Prefser;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午3:28
 */
public abstract class BaseFragment extends Fragment {

    protected Subscription mSubscription;
    protected Prefser prefser;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        prefser = new Prefser(getContext());
        mSubscription = Subscriptions.empty();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }

    protected String getTitle() {
        return "";
    }

    /**
     * findViewById简化
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * findViewById简化
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(View v, int id) {
        return (T) v.findViewById(id);
    }
}

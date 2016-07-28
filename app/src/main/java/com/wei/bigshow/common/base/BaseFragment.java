package com.wei.bigshow.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.prefser.library.Prefser;
import com.wei.bigshow.rx.RxBus;
import com.wei.bigshow.ui.view.LoadingView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午3:28
 */
public abstract class BaseFragment extends Fragment {

    protected Subscription mSubscription;
    protected Prefser prefser;
    protected LoadingView loadingView;
    protected RxBus _rxBus;
    protected CompositeSubscription _subscriptions;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        prefser = new Prefser(getContext());
        mSubscription = Subscriptions.empty();
        _rxBus = RxBus.getDefault();
        _subscriptions = new CompositeSubscription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingView = new LoadingView(getActivity());
        loadingView.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        unsubscribe();
        _subscriptions.clear();
    }

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
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

    public void onEvent(Object object){

    }

    protected String getTitle() {
        return "";
    }
}

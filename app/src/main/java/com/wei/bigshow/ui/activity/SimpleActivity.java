package com.wei.bigshow.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseActivity;
import com.wei.bigshow.ui.fragment.ZeusFragment;

/**
 * describe
 * created by tindle
 * created time 16/7/8 下午10:39
 */
public class SimpleActivity extends BaseActivity{

    public static final String ZEUS = "ZeusFragment";
    public static final String GIPHY_SEARCH = "GiphySearchFragment";

    public static final String FRAGMENT_TYPE = "FragmentType";

    public static void start(Context context, Bundle bundle){
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        String fragmentType = bundle.getString(FRAGMENT_TYPE);
        if(fragmentType==null)
            return;

        if(ZEUS.equals(fragmentType)){
            ft.add(R.id.layout_container, ZeusFragment.instance(bundle), TAG)
                    .commit();
        }else if(GIPHY_SEARCH.equals(fragmentType)){
            ft.add(R.id.layout_container, ZeusFragment.instance(bundle), TAG)
                    .commit();
        }

    }
}

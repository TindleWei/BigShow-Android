package com.wei.bigshow.ui.activity;

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

    public static final String Zeus = "ZeusFragment";

    public static final String FRAGMENT_TYPE = "FragmentType";

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

        if(bundle.getString(FRAGMENT_TYPE).equals(Zeus)){
            ft.add(R.id.layout_container, ZeusFragment.instance(bundle), TAG)
                    .commit();
        }



    }
}

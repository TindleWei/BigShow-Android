package com.gamebuddy.bigshow.view.activity;

import android.os.Bundle;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseRootActivity;
import com.gamebuddy.bigshow.view.fragment.MyStoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 15/12/8 下午4:36
 */
public class RootActivity extends BaseRootActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

        List<HomeTabEntity> tabs = new ArrayList<>();
        tabs.add(new HomeTabEntity("首页", MyStoryFragment.class, R.drawable.ic_tab_1));
        tabs.add(new HomeTabEntity("奥术", MyStoryFragment.class, R.drawable.ic_tab_2));
        tabs.add(new HomeTabEntity("我的", MyStoryFragment.class, R.drawable.ic_tab_3));

        initBottomTabs(tabs);
    }
}

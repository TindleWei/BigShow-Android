package com.wei.bigshow.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseActivity;
import com.wei.bigshow.ui.fragment.CardRevealFragment;
import com.wei.bigshow.ui.fragment.GiphyListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.layout_drawer)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.view_navigation)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.layout_container)
    RelativeLayout layoutContainer;

    /**
     * Bundle key representing the Active Fragment
     */
    private static final String STATE_ACTIVE_FRAGMENT = "active_fragment";
    private Fragment mFragment;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initView();
        initFragment(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, STATE_ACTIVE_FRAGMENT, mFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

    }

    public void initView() {
        setupDrawerMenu();
    }

    public void initFragment(Bundle bundle){
        if (bundle != null) {
            mFragment = getSupportFragmentManager().getFragment(bundle, STATE_ACTIVE_FRAGMENT);
        }
        if (mFragment == null) {
            mFragment = CardRevealFragment.instance();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.layout_container,
                mFragment, mFragment.getClass().getSimpleName()).commit();
        currentFragment = mFragment;

    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment tempFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        String name = fragment.getClass().getSimpleName();
        if (tempFragment==null || !tempFragment.isAdded()) {
            ft.hide(currentFragment).add(R.id.layout_container, fragment, fragment.getClass().getSimpleName()).commit();
            String name2 = fragment.getClass().getSimpleName();
            currentFragment = fragment;
        } else {
            ft.hide(currentFragment).show(tempFragment).commit();
            currentFragment = tempFragment;
            fragment = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_create:
                startActivity(new Intent(mContext, PlotGridActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerMenu() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                changeFragment(GiphyListFragment.instance());
                                //changeFragment(CardRevealFragment.instance());
                                break;
                            case R.id.nav_messages:
                                changeFragment(CardRevealFragment.instance());
                                //changeFragment(MyStoryFragment.instance());
                                break;
                        }
                        return true;
                    }
                });
    }
}

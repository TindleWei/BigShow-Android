package com.wei.bigshow.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

    }

    public void initView() {
        setupDrawerContent(mNavigationView);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("CardRevealFragment") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.layout_container, CardRevealFragment.instance(), "CardRevealFragment")
                    .commit();
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(fm.findFragmentByTag("CardRevealFragment"));
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

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}

package com.gamebuddy.bigshow.common.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.github.pwittchen.prefser.library.Prefser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 导航栏基类,包含底部导航或顶部导航
 */
public abstract class BaseRootActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.viewpager_tab)
    SmartTabLayout viewpagerTab;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    protected Context mContext;

    //Lib for SharedPreferences
    public Prefser prefser;


    public class HomeTabEntity {
        String name;
        Class clazz;
        int iconId; //optional
        public HomeTabEntity(String name, Class clazz){
            this.name = name;
            this.clazz = clazz;
        }
        public HomeTabEntity(String name, Class clazz, int iconId){
            this.name = name;
            this.clazz = clazz;
            this.iconId = iconId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        prefser = new Prefser(this);

        setContentView(R.layout.activity_root);

        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        initToolbar();
    }

    // default eventbus methos
    public void onEvent(Object object) {

    }

    public void initToolbar() {
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        //toolbar.setLogo(R.mipmap.ic_logo_title);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
    }

    /**
     * 默认为顶部的导航
     */
    public void initTabs(final List<HomeTabEntity> tabs){
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
        for (HomeTabEntity entity : tabs) {
            creator.add(entity.name, entity.clazz);
        }
        FragmentPagerItems pages = creator.create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages);
        viewPager.setOffscreenPageLimit(pages.size());
        viewPager.setAdapter(adapter);
        final LayoutInflater inflater = LayoutInflater.from(this);
        viewpagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter pagerAdapter) {
                View view = inflater.inflate(R.layout.custom_tab_icon, container, false);
                ImageView iconView = (ImageView) view.findViewById(R.id.iv_icon);
                iconView.setBackgroundResource(tabs.get(position).iconId);
                TextView textView = (TextView) view.findViewById(R.id.tv_name);
                textView.setText(tabs.get(position).name);
                return view;
            }
        });
        viewpagerTab.setViewPager(viewPager);
        viewpagerTab.setVisibility(pages.size() == 1 ? View.GONE : View.VISIBLE);
    }

    /**
     * 自定义底部导航
     */
    public void initBottomTabs(final List<HomeTabEntity> tabs) {
        initTabs(tabs);

    }

    /**
     * 初始化MaterialDrawer
     *
     * @see <a href="https://github.com/mikepenz/MaterialDrawer"/>
     */
    public void initDrawer() {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("drawer_item_home").withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("drawer_item_settings");
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.material_drawer_badge)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.material_drawer_shadow_top))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar) //show left menu view
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("drawer_item_settings")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    }
                })
                .build();
    }

    /**
     * 检测是否需要评分
     */
    public void checkNeedRating() {
        final SharedPreferences sp = mContext.getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        boolean rated = sp.getBoolean("rated", false);
        if (rated == true) return;
        int openTimes = sp.getInt("open_times", 0);
        if (openTimes == 2) {
            sp.edit().putInt("open_times", 0).apply();
        } else {
            sp.edit().putInt("open_times", ++openTimes).apply();
            return;
        }

        String App_Name = getResources().getString(R.string.app_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(String.format("If you enjoy using %s, would you mind taking a moment to rate it? It won't take more than a minute. Thanks for your support!", App_Name));
        builder.setTitle(null);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sp.edit().putBoolean("rated", true).apply();
                jumpToStore();
            }
        });
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 跳转到应用商店进行评分
     */
    public void jumpToStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName().toString()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else { //Chrome Google Play
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName().toString()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
            }
        }
    }
}

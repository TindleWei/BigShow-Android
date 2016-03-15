package com.gamebuddy.bigshow.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseActivity;
import com.gamebuddy.bigshow.view.fragment.MyStoryFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * describe
 * created by tindle
 * created time 15/12/8 下午4:36
 */
public class RootActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.viewpager_tab)
    SmartTabLayout viewpagerTab;

    @Bind(R.id.tab_line)
    View lineBottom;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_root;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        setupTabView();

        checkLike();
    }

    protected void setupTabView() {

        final List<Integer> iconList = new ArrayList<>();
        final List<String> nameList = new ArrayList<>();
        List<Class> fragmentList = new ArrayList<>();

        iconList.add(R.drawable.ic_tab_1);
        nameList.add("Strategy");
        fragmentList.add(MyStoryFragment.class);

        iconList.add(R.drawable.ic_tab_2);
        nameList.add("Guide");
        fragmentList.add(MyStoryFragment.class);

        iconList.add(R.drawable.ic_tab_3);
        nameList.add("Video");
        fragmentList.add(MyStoryFragment.class);

        FragmentPagerItems.Creator pagesCreator = FragmentPagerItems.with(this);
        for (int i = 0; i < fragmentList.size(); i++) {
            pagesCreator.add(nameList.get(i), fragmentList.get(i));
        }
        FragmentPagerItems pages = pagesCreator.create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages);

        final LayoutInflater inflater = LayoutInflater.from(this);
        viewPager.setOffscreenPageLimit(pages.size());
        viewPager.setAdapter(adapter);
        viewpagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter pagerAdapter) {
                View view = inflater.inflate(R.layout.custom_tab_icon, container, false);
                ImageView iconView = (ImageView) view.findViewById(R.id.iv_icon);
                iconView.setBackgroundResource(iconList.get(position));
                TextView textView = (TextView) view.findViewById(R.id.tv_name);
                textView.setText(nameList.get(position));
                return view;
            }
        });
        viewpagerTab.setViewPager(viewPager);

    }

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

    public void checkLike() {
        final SharedPreferences sp = mContext.getSharedPreferences("gamebuddy", Context.MODE_PRIVATE);
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
}

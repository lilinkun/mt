package com.mingtai.mt.activity;


import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.FragmentsAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.fragment.DeclarationFragment;
import com.mingtai.mt.fragment.HomeFragment;
import com.mingtai.mt.fragment.MeFragment;
import com.mingtai.mt.ui.CustomViewPager;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_fragment)
    CustomViewPager customViewPager;
    @BindView(R.id.menu_bottom_1)
    RelativeLayout menu_bottom_1;
    @BindView(R.id.menu_bottom_2)
    RelativeLayout menu_bottom_2;
    @BindView(R.id.menu_bottom_3)
    RelativeLayout menu_bottom_3;

    private final SparseArray<BaseFragment> sparseArray = new SparseArray<>();
    private List<RelativeLayout> relativeLayouts = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {

//        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        ActivityUtil.addAllActivity(this);

        Eyes.translucentStatusBar(this);

        initFragment();

    }

    public void initFragment() {

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        getMenusFragments();
        fragmentsAdapter.setFragments(sparseArray);
        customViewPager.setAdapter(fragmentsAdapter);

        customViewPager.setOnPageChangeListener(this);

        menu_bottom_1.setSelected(true);

        relativeLayouts.add(menu_bottom_1);
        relativeLayouts.add(menu_bottom_2);
        relativeLayouts.add(menu_bottom_3);

    }

    private void getMenusFragments() {
        sparseArray.put(MingtaiUtil.PAGE_HOMEPAGE, new HomeFragment());
        sparseArray.put(MingtaiUtil.PAGE_MALL, new DeclarationFragment());
        sparseArray.put(MingtaiUtil.PAGE_ME, new MeFragment());
    }

    @OnClick({R.id.menu_bottom_1, R.id.menu_bottom_2, R.id.menu_bottom_3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_bottom_1:
                setMenuBg(menu_bottom_1);
                customViewPager.setCurrentItem(0, false);
                break;

            case R.id.menu_bottom_2:
                setMenuBg(menu_bottom_2);
                customViewPager.setCurrentItem(1, false);
                break;

            case R.id.menu_bottom_3:
                setMenuBg(menu_bottom_3);
                customViewPager.setCurrentItem(2, false);
                break;
        }
    }

    private void setMenuBg(RelativeLayout layout){
        Object o = relativeLayouts.iterator();

        while (((Iterator)o).hasNext()){
            RelativeLayout relativeLayout = (RelativeLayout) ((Iterator) o).next();
            if (relativeLayout == layout){
                relativeLayout.setSelected(true);
            }else {
                relativeLayout.setSelected(false);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitByDoubleClick();
        }
        return false;
    }


    boolean isExit = false;

    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            toast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            setMenuBg(menu_bottom_1);
        }else if (position == 1){
            setMenuBg(menu_bottom_2);
        }else if (position == 2){
            setMenuBg(menu_bottom_3);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}

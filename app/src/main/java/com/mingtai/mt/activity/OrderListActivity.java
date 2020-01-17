package com.mingtai.mt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.mingtai.mt.R;
import com.mingtai.mt.adapter.TabPageAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.OrderListContract;
import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.fragment.AllOrderFragment;
import com.mingtai.mt.fragment.CompletedOrderFragment;
import com.mingtai.mt.fragment.OverOrderFragment;
import com.mingtai.mt.fragment.WaitPayFragment;
import com.mingtai.mt.fragment.WaitReceiveFragment;
import com.mingtai.mt.interf.IPayOrderClickListener;
import com.mingtai.mt.presenter.OrderListPresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/4.
 */
public class OrderListActivity extends BaseActivity implements IPayOrderClickListener, OrderListContract{

    @BindView(R.id.order_list_tablayou)
    SlidingTabLayout orderListTablayou;
    @BindView(R.id.order_list_vp)
    ViewPager orderListVp;
    @BindView(R.id.ll_choose_order)
    LinearLayout linearLayout;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;

    private List<String> mTitles;
    private List<Fragment> fragments = new ArrayList<>();
    private OrderBean selfOrderBean;

    private OrderListPresenter orderListPresenter = new OrderListPresenter();

    AllOrderFragment allOrderFragment = new AllOrderFragment();
    WaitPayFragment waitPayFragment = new WaitPayFragment();
    WaitReceiveFragment waitReceiveFragment = new WaitReceiveFragment();
    CompletedOrderFragment completedOrderFragment = new CompletedOrderFragment();
    OverOrderFragment overOrderFragment = new OverOrderFragment();

    @Override
    public int getLayoutId() {
        return R.layout.activity_orderlist;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        orderListPresenter.onCreate(this, this);
        ActivityUtil.addActivity(this);

        ActivityUtil.addAllActivity(this);
        initData();

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        orderListVp.setAdapter(pageAdapter);
//        orderListTablayou.setupWithViewPager(orderListVp);
//        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        orderListTablayou.setViewPager(orderListVp);
        orderListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    allOrderFragment.setData();
                } else if (position == 1) {
                    waitPayFragment.setData();
                } else if (position == 2) {
                    waitReceiveFragment.setData();
                } else if (position == 3) {
                    completedOrderFragment.setData();
                } else if (position == 4) {
                    overOrderFragment.setData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        allOrderFragment.setPayListener(this);
        waitPayFragment.setPayListener(this);
        waitReceiveFragment.setPayListener(this);
        completedOrderFragment.setPayListener(this);

        if (getIntent() != null && getIntent().getBundleExtra(MingtaiUtil.TYPEID) != null) {

            int position = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("position");

            orderListVp.setCurrentItem(position, false);

        }

    }


    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_back:

                    finish();

                    break;

            }
        }
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("全部");
        mTitles.add("待付款");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("交易成功");

        fragments = new ArrayList<>();
        fragments.add(allOrderFragment);
        fragments.add(waitPayFragment);
        fragments.add(waitReceiveFragment);
        fragments.add(completedOrderFragment);
        fragments.add(overOrderFragment);
    }


    @Override
    public void payMode(OrderBean selfOrderBean, int mode) {
        this.selfOrderBean = selfOrderBean;

        /*if (selfOrderBean.getOrderType() == MingtaiUtil.TIAOBOINT){
            Bundle bundle = new Bundle();
            bundle.putString(MingtaiUtil.ORDERSN, selfOrderBean.getOrderSn());
            bundle.putString(MingtaiUtil.WHERE, "order");
            bundle.putInt("point", selfOrderBean.getIntegral());
            UiHelper.launcherForResultBundle(this, PointActivity.class, 0x0987, bundle);
        }else {*/

            Bundle bundle = new Bundle();
            bundle.putString(MingtaiUtil.ORDERSN, selfOrderBean.getOrderSn());
            bundle.putString(MingtaiUtil.WHERE, "order");
            UiHelper.launcherForResultBundle(this, PayActivity.class, 0x0987, bundle);
//        }
    }

    @Override
    public void SureReceive(final String orderId) {
        new AlertDialog.Builder(this).setMessage("是否确定收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orderListPresenter.sureReceipt(orderId, ProApplication.SESSIONID(OrderListActivity.this));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);
    }

    @Override
    public void getQrcode(String orderId) {

    }

    @Override
    public void sureReceiptSuccess(String collectDeleteBean) {

    }

    @Override
    public void sureReceiptFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x0987) {
                allOrderFragment.setData();
                waitPayFragment.setData();
                waitReceiveFragment.setData();
                completedOrderFragment.setData();
            }
        }
    }
}

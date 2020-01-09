package com.mingtai.mt.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.IntegralAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.IntegralContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.BalanceDetailBean;
import com.mingtai.mt.presenter.IntegralPresenter;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/9.
 */
public class IntegralActivity extends BaseActivity implements IntegralContract {

    @BindView(R.id.rv_style)
    RecyclerView rv_style;
    @BindView(R.id.tv_balance_amount)
    TextView tv_balance_amount;
    @BindView(R.id.tv_balance_name)
    TextView tv_balance_name;
    @BindView(R.id.ic_balance_status)
    LinearLayout ic_balance_status;
    @BindView(R.id.iv_head_left)
    ImageView iv_head_left;
    @BindView(R.id.tv_wait_receiver)
    TextView tv_wait_receiver;
    @BindView(R.id.line_wait_receiver)
    View line_wait_receiver;
    @BindView(R.id.line_list)
    View line_list;
    @BindView(R.id.tv_list)
    TextView tv_list;
    @BindView(R.id.rl_wait_receiver)
    RelativeLayout rl_wait_receiver;
    @BindView(R.id.rl_list)
    RelativeLayout rl_list;

    private IntegralPresenter integralPresenter = new IntegralPresenter();
    private int mListStyle = 0;
    private int PAGE_INDEX = 1;
    private int sizeInt = 0;
    private IntegralAdapter integralAdapter;
    private int lastVisibleItem = 0;
    private BalanceBean balanceBean;
    private String type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    public void initEventAndData() {

        Eyes.translucentStatusBar(this);

        integralPresenter.onCreate(this, this);

        mListStyle = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("style");
        balanceBean = (BalanceBean) getIntent().getBundleExtra(MingtaiUtil.TYPEID).getSerializable(MingtaiUtil.BALANCEBEAN);

        init();

//        CustomPagerAdapter pageAdapter = new CustomPagerAdapter(this,strings);
//        pageAdapter.setTitles(strings);
//        vp_style.setAdapter(pageAdapter);
//        tab_strip.setupWithViewPager(vp_style);
//        tab_strip.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_style.setLayoutManager(linearLayoutManager);

        rv_style.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (integralAdapter != null) {
                        if (lastVisibleItem + 1 == integralAdapter.getItemCount()) {

                            if (PAGE_INDEX * Integer.valueOf(MingtaiUtil.PAGE_COUNT) > pointListBeans.size()) {
//                                toast("已到末尾");
                            } else {
                                PAGE_INDEX++;
                                integralPresenter.getPriceData(PAGE_INDEX + "", MingtaiUtil.PAGE_COUNT, type, ProApplication.SESSIONID(IntegralActivity.this));
                            }
                        }

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.rl_wait_receiver, R.id.rl_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
                intent.putExtra(MingtaiUtil.TYPEID, bundle);
                setResult(RESULT_OK, intent);
                finish();

                break;


            case R.id.rl_wait_receiver:
                type = "4";
                integralAdapter = null;
                line_wait_receiver.setVisibility(View.VISIBLE);
                tv_list.setTextColor(getResources().getColor(R.color.grey_color2));
                line_list.setVisibility(View.GONE);
                tv_wait_receiver.setTextColor(getResources().getColor(R.color.black_333333));
                integralPresenter.getPriceData(PAGE_INDEX + "", MingtaiUtil.PAGE_COUNT, type, ProApplication.SESSIONID(IntegralActivity.this));
                break;

            case R.id.rl_list:
                type = "5";
                integralAdapter = null;
                line_wait_receiver.setVisibility(View.GONE);
                tv_list.setTextColor(getResources().getColor(R.color.black_333333));
                line_list.setVisibility(View.VISIBLE);
                tv_wait_receiver.setTextColor(getResources().getColor(R.color.grey_color2));
                integralPresenter.getPriceData(PAGE_INDEX + "", MingtaiUtil.PAGE_COUNT, type, ProApplication.SESSIONID(IntegralActivity.this));
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
            intent.putExtra(MingtaiUtil.TYPEID, bundle);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private ArrayList<BalanceDetailBean> pointListBeans;


    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> amountPriceBean) {
        rv_style.setVisibility(View.VISIBLE);
        if (integralAdapter == null) {
            pointListBeans = amountPriceBean;
            if (mListStyle == 0) {
                integralAdapter = new IntegralAdapter(this, amountPriceBean, 0);
            } else {
                if (type.equals("4")) {
                    integralAdapter = new IntegralAdapter(this, amountPriceBean, 2);
                } else {
                    integralAdapter = new IntegralAdapter(this, amountPriceBean, 1);
                }
            }
            rv_style.setAdapter(integralAdapter);
//            sizeInt = amountPriceBean.getAmount_list().size();

        } else {
            pointListBeans.addAll(amountPriceBean);
            integralAdapter.setData(pointListBeans);

        }
    }

    @Override
    public void getDataFail(String msg) {
        if (msg.contains("查无数据")) {
            rv_style.setVisibility(View.GONE);
            if (pointListBeans != null && pointListBeans.size() > 0) {
                pointListBeans.clear();
            }
        }
    }


    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
        init();
    }

    @Override
    public void getBalanceFail(String msg) {
        toast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0x222) {
            integralPresenter.getBalance(ProApplication.SESSIONID(this));
            PAGE_INDEX = 1;
            init();
        } else if (resultCode == RESULT_OK && requestCode == 0x1122) {
            this.balanceBean = (BalanceBean) data.getBundleExtra(MingtaiUtil.TYPEID).getSerializable(MingtaiUtil.BALANCEBEAN);
            integralPresenter.getBalance(ProApplication.SESSIONID(this));
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init() {
        if (mListStyle == 0) {
            type = "2";
            integralPresenter.getPriceData(PAGE_INDEX + "", MingtaiUtil.PAGE_COUNT, type, ProApplication.SESSIONID(this));
            tv_balance_name.setText(getString(R.string.net_coin));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_integral_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back_white);
            tv_balance_name.setTextColor(getResources().getColor(R.color.white));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.white));
            tv_balance_amount.setText(balanceBean.getMoney2Balance() + "");
            rl_wait_receiver.setVisibility(View.INVISIBLE);
            rl_list.setVisibility(View.INVISIBLE);
        } else if (mListStyle == 1) {
            type = "5";
            integralPresenter.getPriceData(PAGE_INDEX + "", MingtaiUtil.PAGE_COUNT, type, ProApplication.SESSIONID(this));
            tv_balance_name.setText(getString(R.string.discount_coin));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_wlm_coin_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back);
            tv_balance_name.setTextColor(getResources().getColor(R.color.black_333333));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.black_333333));
            tv_balance_amount.setText(balanceBean.getMoney5Balance() + "");
        }
    }
}

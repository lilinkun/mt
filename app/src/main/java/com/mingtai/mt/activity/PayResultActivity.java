package com.mingtai.mt.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.PayResultContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.presenter.PayResultPresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/8.
 */
public class PayResultActivity extends BaseActivity implements PayResultContract {

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_see_order)
    TextView tv_see_order;
    @BindView(R.id.tv_back_home)
    TextView tv_back_home;

    private String orderid = "";
    private String orderTpye = "";
    private String teamid = "";

    private PayResultPresenter payResultPresenter = new PayResultPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        ActivityUtil.addAllActivity(this);
        ActivityUtil.addHomeActivity(this);
        ActivityUtil.addActivity(this);

        String price = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.PRICE);
        orderid = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.ORDERSN);

        orderTpye = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.GOODSTYPE);

        tv_price.setText("¥ " + price);

        payResultPresenter.onCreate(this, this);

        payResultPresenter.orderDetail(orderid, ProApplication.SESSIONID(this));


    }

    @OnClick({R.id.tv_see_order, R.id.tv_back_home, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_see_order:

//                Bundle bundle = new Bundle();
//                bundle.putString("goodsname", "");
//                UiHelper.launcherBundle(this, OrderListActivity.class,bundle);

                Bundle bundle = new Bundle();
                bundle.putInt("status", 1);
                bundle.putString("order_sn", orderid);

                UiHelper.launcherBundle(this, OrderDetailActivity.class, bundle);
                setResult(RESULT_OK);
                finish();


                break;

            case R.id.tv_back_home:


                ActivityUtil.finishAll();

                setResult(RESULT_OK);
                finish();

//                Intent intent = new Intent(PayResultActivity.this, MainFragmentActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;

            case R.id.ll_back:
                setResult(RESULT_OK);
                finish();
//                ActivityUtil.finishHomeAll();

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setDataSuccess(OrderDetailInfo orderDetailBeans) {
        teamid = orderDetailBeans.getOrder().getFreezeId();
    }

    @Override
    public void setDataFail(String msg) {

    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
    }

    @Override
    public void getBalanceFail(String msg) {

    }
}

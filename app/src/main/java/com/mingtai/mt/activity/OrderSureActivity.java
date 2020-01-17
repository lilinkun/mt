package com.mingtai.mt.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.OrderAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.OrderListContract;
import com.mingtai.mt.contract.OrderSureContract;
import com.mingtai.mt.entity.ChooseItemBean;
import com.mingtai.mt.entity.CustomPriceBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.presenter.OrderListPresenter;
import com.mingtai.mt.presenter.OrderSurePresenter;
import com.mingtai.mt.ui.CommendRecyclerView;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/7.
 */
public class OrderSureActivity extends BaseActivity implements OrderSureContract {

    @BindView(R.id.tv_no_address)
    TextView tv_no_address;
    @BindView(R.id.tv_goods_consignee_name)
    TextView tv_goods_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.rv_order)
    CommendRecyclerView commendRecyclerView;
    @BindView(R.id.goods_total_price)
    TextView goods_total_price;
    @BindView(R.id.tv_fare)
    TextView tv_fare;
    @BindView(R.id.tv_use_point)
    TextView tv_use_point;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_total_goods_price)
    TextView tv_total_goods_price;
    @BindView(R.id.ll_bottom_price)
    LinearLayout ll_bottom_price;
    @BindView(R.id.tv_settlement)
    TextView tv_settlement;
    @BindView(R.id.tv_order_type)
    TextView tv_order_type;
    @BindView(R.id.tv_order_name)
    TextView tv_order_name;
    @BindView(R.id.tv_customer_name)
    TextView tv_customer_name;

    private ArrayList<ChooseItemBean> chooseItemBeans;
    private ArrayList<GoodsBean> goodsBeans;
    private String goodsIdStr = "";
    private String goodsNum = "";
    private StoreInfoAddressBean addressBean;
    private CustomPriceBean customPriceBean;
    private OrderSurePresenter orderListPresenter = new OrderSurePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        orderListPresenter.onCreate(this,this);

        ActivityUtil.addAllActivity(this);
        ActivityUtil.addHomeActivity(this);
        ActivityUtil.addActivity(this);
        chooseItemBeans = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getParcelableArrayList("ChooseItemBeans");
        goodsBeans = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getParcelableArrayList("GOODSBEANS");
        addressBean = (StoreInfoAddressBean)getIntent().getBundleExtra(MingtaiUtil.TYPEID).getSerializable("ADDRESS");
        customPriceBean = (CustomPriceBean)getIntent().getBundleExtra(MingtaiUtil.TYPEID).getSerializable("CUSTOMPRICEBEAN");

        if (addressBean != null){
            tv_no_address.setVisibility(View.GONE);
            tv_goods_consignee_name.setText(addressBean.getName());
            tv_consignee_phone.setText(addressBean.getMobile());
            tv_consignee_address.setText(addressBean.getProvince_name()+addressBean.getCity_name()+addressBean.getArea_name()+addressBean.getAddress());
        }else {
            tv_no_address.setVisibility(View.VISIBLE);
        }

        ll_bottom_price.setVisibility(View.GONE);

        goods_total_price.setText(customPriceBean.getGoodsPrice());
        tv_fare.setText(customPriceBean.getShippingFreePrice());
        tv_use_point.setText(customPriceBean.getPoint());
        tv_total.setText(customPriceBean.getGoodsTotalPrice());
        tv_total_goods_price.setText(customPriceBean.getGoodsTotalPrice());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置分割线

        commendRecyclerView.setLayoutManager(linearLayoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(this,goodsBeans,chooseItemBeans);
        commendRecyclerView.setAdapter(orderAdapter);

        tv_settlement.setText("提交订单");

        if (customPriceBean.getGoodsType() == MingtaiUtil.SALEINT){
            tv_order_type.setText("消费订单");
        }else if (customPriceBean.getGoodsType() == MingtaiUtil.TIAOBOINT){
            tv_order_type.setText("调拨订单");
        }else if (customPriceBean.getGoodsType() == MingtaiUtil.UPDATEINT){
            tv_order_type.setText("升级订单");
        }

        tv_order_name.setText(ProApplication.mAccountBean.getUserName());

        tv_customer_name.setText(addressBean.getUserName());

    }

    @OnClick({R.id.tv_settlement,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_settlement:

                goodsIdStr = "";
                goodsNum = "";

                for (int i = 0; i < chooseItemBeans.size(); i++) {
                    if (goodsIdStr.equals("") && goodsNum.equals("")) {
                        goodsIdStr = chooseItemBeans.get(i).getGoodsId();
                        goodsNum = chooseItemBeans.get(i).getNum() + "";
                    } else {
                        goodsIdStr = goodsIdStr + ("," + chooseItemBeans.get(i).getGoodsId());
                        goodsNum = goodsNum + "," + chooseItemBeans.get(i).getNum();
                    }
                }

                if (customPriceBean.getGoodsType() == MingtaiUtil.UPDATEINT){
                    orderListPresenter.settlement(goodsIdStr,customPriceBean.getUserlevel()+"", tv_total_goods_price.getText().toString(), customPriceBean.getShippingFreePrice(), goodsNum
                            , addressBean.getName(), addressBean.getMobile(), ProApplication.STORENO, customPriceBean.getDeliveryMethod() + "", addressBean.getProv() + "",
                            addressBean.getCity() + "", addressBean.getArea() + "", customPriceBean.getGoodsType() + "", addressBean.getAddress(), addressBean.getUserName(), addressBean.getPost(),
                            "", customPriceBean.getPoint(), ProApplication.SESSIONID(this));
                }else {
                    orderListPresenter.settlement(goodsIdStr, tv_total_goods_price.getText().toString(), customPriceBean.getShippingFreePrice(), goodsNum
                            , addressBean.getName(), addressBean.getMobile(), ProApplication.STORENO, customPriceBean.getDeliveryMethod() + "", addressBean.getProv() + "",
                            addressBean.getCity() + "", addressBean.getArea() + "", customPriceBean.getGoodsType() + "", addressBean.getAddress(), addressBean.getUserName(), addressBean.getPost(),
                            "", customPriceBean.getPoint(), ProApplication.SESSIONID(this));
                }

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeHomeActivity(this);
        ActivityUtil.removeActivity(this);
    }

    @Override
    public void getTlementSuccess(String msg) {

        if (customPriceBean.getGoodsType() == MingtaiUtil.TIAOBOINT){

            Bundle bundle = new Bundle();
            bundle.putString(MingtaiUtil.ORDERSN, msg);
            bundle.putString(MingtaiUtil.WHERE, "goods");
            bundle.putInt("point", Integer.valueOf(customPriceBean.getPoint()));
            UiHelper.launcherForResultBundle(this, PointActivity.class, 0x0987, bundle);
        }else {

            Bundle bundle = new Bundle();
            bundle.putString(MingtaiUtil.ORDERSN, msg);
            bundle.putInt(MingtaiUtil.GOODSTYPE, customPriceBean.getGoodsType());
            bundle.putString(MingtaiUtil.WHERE, "goods");
            UiHelper.launcherBundle(this, PayActivity.class, bundle);
            ActivityUtil.finishHomeAll();

        }
    }

    @Override
    public void getTlementFail(String msg) {
        toast(msg);
    }
}


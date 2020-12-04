package com.mingtai.mt.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.OrderChildAdapter;
import com.mingtai.mt.adapter.OrderProductAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.AllOrderContract;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.presenter.OrderDetailPresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/8.
 */
public class OrderDetailActivity extends BaseActivity implements AllOrderContract, OrderChildAdapter.OnItemClickListener {

    @BindView(R.id.order_sn)
    TextView order_sn;
    @BindView(R.id.order_date)
    TextView order_date;
    @BindView(R.id.goods_total_price)
    TextView goods_total_price;
    @BindView(R.id.tv_use_point)
    TextView tv_use_point;
    @BindView(R.id.tv_fare)
    TextView tv_fare;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.rv_order)
    RecyclerView recyclerView;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_no_address)
    TextView tv_no_address;
    @BindView(R.id.tv_exit_order)
    TextView tv_exit_order;
    @BindView(R.id.tv_pay_order)
    TextView tv_pay_order;
    @BindView(R.id.tv_order_pay_price)
    TextView tv_order_pay_price;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.ll_price_status)
    LinearLayout ll_price_status;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.tv_pay_style)
    TextView tv_pay_style;
    @BindView(R.id.tv_pay_message)
    TextView tv_pay_message;
    @BindView(R.id.pay_date)
    TextView tv_pay_date;
    @BindView(R.id.ll_pay_date)
    LinearLayout ll_pay_date;
    @BindView(R.id.send_out_date)
    TextView send_out_date;
    @BindView(R.id.logistics_information)
    TextView logistics_information;
    @BindView(R.id.iv_order_status)
    ImageView iv_order_status;
    @BindView(R.id.rl_need_integral)
    RelativeLayout rl_need_integral;
    @BindView(R.id.view_need_integral)
    View view_need_integral;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;
    @BindView(R.id.ll_lgs)
    LinearLayout ll_lgs;
    @BindView(R.id.iv_address_right)
    ImageView iv_address_right;
    @BindView(R.id.ll_lgs_time)
    LinearLayout ll_lgs_time;
    @BindView(R.id.tv_query_logistics)
    TextView tv_query_logistics;
    @BindView(R.id.rl_need_pay)
    RelativeLayout rl_need_pay;
    @BindView(R.id.rl_goods_price)
    RelativeLayout rl_goods_price;

    private  OrderDetailPresenter orderDetailPresenter = new OrderDetailPresenter();
    private String orderSn = "";
    private int status = 0;
    private OrderDetailBean orderDetailBeans;
    double order_amount = 0;
    double payid = 0;
    double useIntegral = 0;
    double shipping_fee = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_order;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        ActivityUtil.addAllActivity(this);
        orderDetailPresenter.onCreate(this,this);

        orderSn = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("order_sn");
        status = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("status");
        order_sn.setText(orderSn);

        orderDetailPresenter.onCreate(this, this);

        orderDetailPresenter.cartBuy(orderSn, ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        rv_product.setLayoutManager(linearLayoutManager1);

//        WXPayEntryActivity.setPayListener(this);

        iv_address_right.setVisibility(View.GONE);

        rl_need_pay.setVisibility(View.GONE);
        rl_goods_price.setVisibility(View.GONE);
    }


    @OnClick({R.id.ll_back, R.id.ll_lgs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                setResult(RESULT_OK);
                finish();

                break;

            case R.id.ll_lgs:

                Bundle bundle = new Bundle();
                bundle.putString("type", "lgs");
                bundle.putString("ordersn", orderSn);
                bundle.putString("CategoryName","物流信息");
                UiHelper.launcherBundle(this, WebviewActivity.class, bundle);

                break;
        }
    }

    @Override
    public void setDataSuccess(final OrderDetailInfo orderDetailBean) {

        if (orderDetailBean.getOrder().getOrderType() != 2){
            rl_need_pay.setVisibility(View.VISIBLE);
        }

        this.orderDetailBeans = orderDetailBean.getOrder();
        OrderChildAdapter orderChildAdapter = new OrderChildAdapter(this, orderDetailBeans.getOrderDetail());
        recyclerView.setAdapter(orderChildAdapter);

        OrderProductAdapter orderChildAdapter1 = new OrderProductAdapter(this, orderDetailBeans.getListProduct());

        rv_product.setAdapter(orderChildAdapter1);

        orderChildAdapter.setItemClickListener(this);

        order_date.setText(orderDetailBeans.getCreateDate());

        status = orderDetailBeans.getOrderStatus();
        tv_pay_style.setText(orderDetailBeans.getOrderStatusName());

        order_amount += orderDetailBeans.getOrderAmount();

        BigDecimal b = new BigDecimal(order_amount);
        order_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        useIntegral += orderDetailBeans.getIntegral();
        shipping_fee += orderDetailBeans.getShippingFree();
        payid += orderDetailBeans.getGoodsAmount();

        if (useIntegral == 0) {
            rl_need_integral.setVisibility(View.GONE);
            view_need_integral.setVisibility(View.GONE);
        }

        if (ll_point != null) {
            ll_point.setVisibility(View.GONE);
        }

        goods_total_price.setText("¥" + payid + "");
        tv_use_point.setText("" + useIntegral);
        tv_fare.setText(shipping_fee + "");
        tv_total.setText("¥" + order_amount + "");
        tv_order_pay_price.setText("¥" + order_amount + "");

        tv_consignee_address.setText(orderDetailBeans.getAddressName() + orderDetailBeans.getAddress());
        tv_consignee_name.setText(orderDetailBeans.getConsignee());
        tv_consignee_phone.setText(orderDetailBeans.getMobile());
        tv_no_address.setVisibility(View.GONE);

        if (status == 1 || status == 2 || status == 4) {
            ll_pay_date.setVisibility(View.VISIBLE);
            tv_pay_date.setText(orderDetailBeans.getPayDate() + "");
        }

        if (status == 1) {
            tv_pay_message.setText("买家已付款，等待发货");
            rl_bottom.setVisibility(View.GONE);
            iv_order_status.setImageResource(R.mipmap.ic_order_status_pay);
        } else if (status == 2) {
            tv_exit_order.setVisibility(View.GONE);
            tv_pay_order.setText("确认收货");
            tv_pay_message.setText("您的商品正在运输中");
            tv_query_logistics.setVisibility(View.VISIBLE);
            iv_order_status.setImageResource(R.mipmap.ic_order_status_unover);
        } else if (status == 0) {
            tv_exit_order.setText("取消订单");
            tv_pay_order.setText("立即付款");
            tv_pay_message.setText("您的订单已提交，请尽快完成支付，确保宝贝早日到达您的身边。");
            iv_order_status.setImageResource(R.mipmap.ic_order_status_unpay);
        } else if (status == 4) {
            tv_pay_message.setText("您的交易已经完成");
            tv_pay_order.setText("删除订单");
            tv_query_logistics.setVisibility(View.VISIBLE);
            ll_price_status.setVisibility(View.GONE);
            tv_exit_order.setVisibility(View.GONE);
            tv_pay_order.setVisibility(View.GONE);
//            rl_bottom.setVisibility(View.GONE);
            iv_order_status.setImageResource(R.mipmap.ic_order_status_over);
        } else if (status == 5) {
            tv_pay_message.setText("");
            rl_bottom.setVisibility(View.GONE);
        } else if(status == 6){
            tv_pay_message.setText("买家已申请退款，等待商家审核");
            rl_bottom.setVisibility(View.GONE);
        } else if (status == 3){
            tv_pay_message.setText("");
            rl_bottom.setVisibility(View.GONE);
        } else if (status == 8){
            tv_pay_message.setText("");
            rl_bottom.setVisibility(View.GONE);
        }


        if (orderDetailBeans.getSendStatus() > 0){
            ll_lgs.setVisibility(View.VISIBLE);
            ll_lgs_time.setVisibility(View.VISIBLE);
        }


//        send_out_date.setText(orderDetailBeans.getShippingDate() + "");
        logistics_information.setText(orderDetailBeans.getLgsName() + " " + orderDetailBeans.getLgsNumber());

        tv_exit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ButtonUtils.isFastDoubleClick()) {

                    if (status == 1 || status == 2){
                        new AlertDialog.Builder(OrderDetailActivity.this).setMessage("您确定要申请退款？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orderDetailPresenter.cancelOrder(orderDetailBeans.getOrderSn(), ProApplication.SESSIONID(OrderDetailActivity.this));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }else {
                        new AlertDialog.Builder(OrderDetailActivity.this).setTitle("温馨提示").setMessage("您确定要取消订单？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orderDetailPresenter.exitOrder(orderDetailBeans.getOrderId(), ProApplication.SESSIONID(OrderDetailActivity.this));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                }

            }
        });
        tv_pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick()) {
//                    tv_pay_order.setClickable(false);
                    if (status == 0) {

                        if (orderDetailBeans.getOrderType() == MingtaiUtil.TIAOBOINT){
                            Bundle bundle = new Bundle();
                            bundle.putString(MingtaiUtil.ORDERSN, orderDetailBeans.getOrderSn());
                            bundle.putString(MingtaiUtil.USERNAME, orderDetailBeans.getUserName());
                            bundle.putString(MingtaiUtil.WHERE, "order");
                            bundle.putInt("point", orderDetailBeans.getIntegral());
                            UiHelper.launcherForResultBundle(OrderDetailActivity.this, PointActivity.class, 0x1231, bundle);
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString(MingtaiUtil.ORDERSN, orderDetailBeans.getOrderSn() + "");
                            bundle.putString(MingtaiUtil.WHERE, "order");
                            UiHelper.launcherForResultBundle(OrderDetailActivity.this, PayActivity.class, 0x1231, bundle);
                        }
//                        allOrderPresenter.getOrderData(ProApplication.SESSIONID(AllOrderActivity.this));
                    } else if (status == 2) {

                        new AlertDialog.Builder(OrderDetailActivity.this).setMessage("是否确定收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orderDetailPresenter.sureReceipt(orderDetailBeans.getOrderSn() + "", ProApplication.SESSIONID(OrderDetailActivity.this));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    } else if (status == 4) {
//                        allOrderPresenter.deleteOrder(orderDetailBeans.getOrderId()+"", ProApplication.SESSIONID(AllOrderActivity.this));
                    }
                }
            }
        });
    }

    @Override
    public void setDataFail(String msg) {

    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {
        if (!tv_pay_order.isClickable()) {
            tv_pay_order.setClickable(true);
        }
        toast("取消订单成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void exitOrderFail(String msg) {
        toast(msg);
    }

    @Override
    public void sureReceiptSuccess(String collectDeleteBean) {

    }

    @Override
    public void sureReceiptFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {

        String goodsid = orderDetailBeans.getOrderDetail().get(position).getGoodsId();
        Bundle bundle = new Bundle();
        bundle.putString("GOODSID", goodsid);
        UiHelper.launcherBundle(this,GoodsDetailActivity.class,bundle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
        }


        return super.onKeyDown(keyCode, event);
    }
}

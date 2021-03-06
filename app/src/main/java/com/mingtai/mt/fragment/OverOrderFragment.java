package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.OrderDetailActivity;
import com.mingtai.mt.adapter.SelfOrderAdapter;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.SelfOrderContract;
import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.presenter.SelfOrderPresenter;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/1/6.
 */

public class OverOrderFragment extends BaseFragment implements SelfOrderContract, SelfOrderAdapter.OnItemClick, SelfOrderAdapter.OnItemClickListener {

    @BindView(R.id.over_pay_rv)
    RecyclerView over_pay_rv;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_no_order)
    LinearLayout ll_no_order;

    SelfOrderPresenter selfOrderPresenter = new SelfOrderPresenter();
    private int pageIndex = 1;
    private SelfOrderAdapter selfOrderAdapter;
    private ArrayList<OrderBean> selfOrderBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_order_over;
    }

    @Override
    public void initEventAndData() {
        selfOrderPresenter.onCreate(getActivity(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        over_pay_rv.setLayoutManager(linearLayoutManager);

        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));
            }
        });
    }

    public void setData() {
        if (getActivity() != null) {
            selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));
        }
    }

    @Override
    public void getDataSuccess(ArrayList<OrderBean> selfOrderBeans) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        over_pay_rv.setVisibility(View.VISIBLE);
        if (selfOrderBeans.size() > 0) {
            this.selfOrderBeans = selfOrderBeans;
            if (selfOrderAdapter == null) {
                selfOrderAdapter = new SelfOrderAdapter(getActivity(), selfOrderBeans, this);

                over_pay_rv.setAdapter(selfOrderAdapter);
                over_pay_rv.setVisibility(View.VISIBLE);
                ll_no_order.setVisibility(View.GONE);
                selfOrderAdapter.setItemClickListener(this);
            } else {
                selfOrderAdapter.setData(selfOrderBeans);
            }
        } else {
            ll_no_order.setVisibility(View.VISIBLE);
            over_pay_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        ll_no_order.setVisibility(View.VISIBLE);
        if (msg.contains("查无数据")) {
            over_pay_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {

    }

    @Override
    public void exitOrderFail(String smg) {

    }

    @Override
    public void cancelOrderSuccess(String collectDeleteBean) {

    }

    @Override
    public void cancelOrderFail(String smg) {

    }

    @Override
    public void exit_order(String orderId) {

    }

    @Override
    public void go_pay(OrderBean orderId) {

    }

    @Override
    public void sureReceipt(String orderId) {

    }

    @Override
    public void cancelOrder(String orderId) {
    }


    @Override
    public void getQrcode(String orderId) {

    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putInt("status", selfOrderBeans.get(position).getOrderStatus());
            bundle.putString("order_sn", selfOrderBeans.get(position).getOrderSn());
            UiHelper.launcherForResultBundle(getActivity(), OrderDetailActivity.class, 0x0987, bundle);
        }
    }
}

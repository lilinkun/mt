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
import com.mingtai.mt.base.BasePagerFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.SelfOrderContract;
import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.interf.IPayOrderClickListener;
import com.mingtai.mt.presenter.SelfOrderPresenter;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2018/12/5.
 */

public class WaitReceiveFragment extends BasePagerFragment implements SelfOrderContract, SelfOrderAdapter.OnItemClick, SelfOrderAdapter.OnItemClickListener {

    @BindView(R.id.wait_receive_rv)
    RecyclerView waitReceiveRv;
    @BindView(R.id.ll_no_order)
    LinearLayout ll_no_order;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    SelfOrderPresenter selfOrderPresenter = new SelfOrderPresenter();
    private ArrayList<OrderBean> selfOrderBeans;
    private String orderId;
    private SelfOrderAdapter selfOrderAdapter;
    private IPayOrderClickListener payListener;

    private int pageIndex = 1;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_wait_receive;
    }

    @Override
    public void initEventAndData() {
        selfOrderPresenter.onCreate(getActivity(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "1", ProApplication.SESSIONID(getActivity()));
            }
        });

        waitReceiveRv.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.custom_divider));
//        waitReceiveRv.addItemDecoration(divider);

        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "1", ProApplication.SESSIONID(getActivity()));
    }

    public void setData() {
        if (getActivity() != null) {
            selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "1", ProApplication.SESSIONID(getActivity()));
        }
    }

    @Override
    public void getDataSuccess(ArrayList<OrderBean> selfOrderBeans) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        waitReceiveRv.setVisibility(View.VISIBLE);

        if (selfOrderBeans.size() > 0) {
            this.selfOrderBeans = selfOrderBeans;
            if (selfOrderAdapter == null) {
                selfOrderAdapter = new SelfOrderAdapter(getActivity(), selfOrderBeans, this);

                waitReceiveRv.setAdapter(selfOrderAdapter);
                selfOrderAdapter.setItemClickListener(this);
                ll_no_order.setVisibility(View.GONE);
            } else {
                selfOrderAdapter.setData(selfOrderBeans);
            }
        } else {
            ll_no_order.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        ll_no_order.setVisibility(View.VISIBLE);
        if (msg.contains("查无数据")) {
            waitReceiveRv.setVisibility(View.GONE);
        }
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {

        for (int i = 0; i < selfOrderBeans.size(); i++) {
            if (selfOrderBeans.get(i).getOrderSn().equals(orderId)) {
                selfOrderBeans.remove(i);
                if (selfOrderAdapter != null) {
                    selfOrderAdapter.setData(selfOrderBeans);
                }
            }
        }
    }

    @Override
    public void exitOrderFail(String smg) {

    }

    @Override
    public void cancelOrderSuccess(String collectDeleteBean) {

        for (int i = 0; i < selfOrderBeans.size(); i++) {
            if (selfOrderBeans.get(i).getOrderSn().equals(orderId)) {
                selfOrderBeans.remove(i);
                if (selfOrderAdapter != null) {
                    selfOrderAdapter.setData(selfOrderBeans);
                }
            }
        }
    }

    @Override
    public void cancelOrderFail(String smg) {

    }

    @Override
    public void exit_order(String orderId) {
        this.orderId = orderId;
        selfOrderPresenter.exitOrder(orderId, ProApplication.SESSIONID(getActivity()));
    }

    @Override
    public void go_pay(OrderBean orderId) {
    }

    @Override
    public void sureReceipt(String orderId) {
    }


    @Override
    public void cancelOrder(String orderId) {
        this.orderId = orderId;
        selfOrderPresenter.cancelOrder(orderId, ProApplication.SESSIONID(getActivity()));
    }


    @Override
    public void getQrcode(String orderId) {
        payListener.getQrcode(orderId);
    }

    public void setPayListener(IPayOrderClickListener payListener) {
        this.payListener = payListener;
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

    @Override
    public void loadData() {
        selfOrderPresenter.getOrderData("1", "20", "1", ProApplication.SESSIONID(getActivity()));
    }
}

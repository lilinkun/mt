package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.mingtai.mt.util.UToast;
import com.mingtai.mt.util.UiHelper;


import java.util.ArrayList;

import butterknife.BindView;

/**
 * 全部订单
 * Created by LG on 2018/12/5.
 */
public class AllOrderFragment extends BasePagerFragment implements SelfOrderContract, SelfOrderAdapter.OnItemClick, SelfOrderAdapter.OnItemClickListener {

    @BindView(R.id.all_order_rv)
    RecyclerView allOrderRv;
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
    private int lastVisibleItem = 0;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_all_order;
    }

    @Override
    public void initEventAndData() {

        selfOrderPresenter.onCreate(getActivity(), this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        allOrderRv.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.custom_divider));
//        allOrderRv.addItemDecoration(divider);

        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));
            }
        });

        allOrderRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (selfOrderAdapter != null) {
                        if (lastVisibleItem + 1 == selfOrderAdapter.getItemCount()) {
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                            if (pageIndex * Integer.valueOf(MingtaiUtil.PAGE_COUNT) > selfOrderBeans.size()) {
                                UToast.show(getActivity(), "已到末尾");
                            } else {
                                pageIndex++;
                                selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));
                            }
//                                }
//                            }, 200);
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

    public void setData() {
        if (getActivity() != null) {
            selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));
        }
    }

    public void setPayListener(IPayOrderClickListener payListener) {
        this.payListener = payListener;
    }


    @Override
    public void getDataSuccess(ArrayList<OrderBean> selfOrderBeans) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        allOrderRv.setVisibility(View.VISIBLE);
        if (selfOrderBeans.size() > 0) {

            if (selfOrderAdapter == null) {
                this.selfOrderBeans = selfOrderBeans;
                selfOrderAdapter = new SelfOrderAdapter(getActivity(), selfOrderBeans, this);
                allOrderRv.setAdapter(selfOrderAdapter);
                ll_no_order.setVisibility(View.GONE);
                selfOrderAdapter.setItemClickListener(this);
            } else {
                if (pageIndex == 1) {
                    this.selfOrderBeans = selfOrderBeans;
                } else {
                    this.selfOrderBeans.addAll(selfOrderBeans);
                }
                selfOrderAdapter.setData(this.selfOrderBeans);
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
        if (msg.contains("查无数据")) {
            allOrderRv.setVisibility(View.GONE);
        }
        ll_no_order.setVisibility(View.VISIBLE);
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {

        /*for (int i = 0; i < selfOrderBeans.size(); i++) {
            if (selfOrderBeans.get(i).getOrderSn().equals(orderId)) {
                selfOrderBeans.remove(i);
                if (selfOrderAdapter != null) {
                    selfOrderAdapter.setData(selfOrderBeans);
                }
            }
        }*/

        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));

    }

    @Override
    public void exitOrderFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void cancelOrderSuccess(String collectDeleteBean) {
        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));
    }

    @Override
    public void cancelOrderFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void exit_order(String orderId) {
        this.orderId = orderId;
        selfOrderPresenter.exitOrder(orderId, ProApplication.SESSIONID(getActivity()));
    }

    @Override
    public void go_pay(OrderBean orderId) {
        payListener.payMode(orderId, 1);
    }

    @Override
    public void sureReceipt(String orderId) {
        payListener.SureReceive(orderId);
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
        selfOrderPresenter.getOrderData(pageIndex + "", MingtaiUtil.PAGE_COUNT, "", ProApplication.SESSIONID(getActivity()));
    }
}

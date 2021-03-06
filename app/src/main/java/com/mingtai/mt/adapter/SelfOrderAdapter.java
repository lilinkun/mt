package com.mingtai.mt.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.entity.SelfOrderInfoBean;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2020/1/8.
 */
public class SelfOrderAdapter extends RecyclerView.Adapter<SelfOrderAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<OrderBean> selfOrderBeans;
    private OnItemClick onItemClick;
    private OnItemClickListener mItemClickListener;

    public SelfOrderAdapter(Context context, ArrayList<OrderBean> selfOrderBeans, OnItemClick onItemClick) {
        this.context = context;
        this.selfOrderBeans = selfOrderBeans;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_self_orderlist, null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.itemView.setTag(position);
//        holder.tv_ship_pay.setText(ShipStatusEnum.getPageByValue(selfOrderBeans.get(position).getOrderStatus()).getStatusMsg()+"");
        holder.tv_ship_pay.setText(selfOrderBeans.get(position).getOrderStatusName() + "");
        double price = selfOrderBeans.get(position).getOrderAmount();

        BigDecimal b = new BigDecimal(price);
        price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        holder.tv_integral.setText("分值:");
//        holder.tv_integral.setText("(含运费" + selfOrderBeans.get(position).getShippingFree()  + ")" + "、分值:");
        holder.tv_point_amount.setText("" + selfOrderBeans.get(position).getIntegral());

        if(selfOrderBeans.get(position).getOrderType() != 2) {
            holder.tv_order_amount.setText("¥" + price + " ");
            holder.tv_total_name.setText("合计：");
        }

        int num = 0;
        ArrayList<SelfOrderInfoBean> selfOrderInfoBeans = selfOrderBeans.get(position).getList();
        for (int i = 0; i < selfOrderInfoBeans.size(); i++) {
            num += selfOrderInfoBeans.get(i).getGoodsNumber();
//            num += (SelfOrderInfoBean)(selfOrderBeans.get(position).getList().get(i));
        }
        holder.tv_goods_num.setText("共" + num + "件商品");

        holder.tv_order_time.setText(selfOrderBeans.get(position).getCreateDate());

        if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_PAID) {
            holder.tv_exit_order.setVisibility(View.GONE);
            holder.tv_go_pay.setVisibility(View.GONE);
        } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_NOPAY) {
            holder.tv_exit_order.setVisibility(View.VISIBLE);
            holder.tv_go_pay.setVisibility(View.VISIBLE);


        } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_SHIPPED) {
            holder.tv_go_pay.setVisibility(View.VISIBLE);
            holder.tv_go_pay.setText("确认收货");
            holder.tv_query_logistics.setVisibility(View.GONE);
            holder.tv_exit_order.setVisibility(View.GONE);
        } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_SUCCESSFUL_TRADE) {
            holder.tv_go_pay.setVisibility(View.GONE);
            holder.tv_exit_order.setVisibility(View.GONE);
            holder.tv_query_logistics.setVisibility(View.GONE);
        } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_FAIL) {
            holder.tv_exit_order.setVisibility(View.GONE);
            holder.tv_go_pay.setVisibility(View.GONE);
        } else if  (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_EXIT) {
            holder.tv_exit_order.setVisibility(View.GONE);
            holder.tv_go_pay.setVisibility(View.GONE);
        } else {
            holder.tv_exit_order.setVisibility(View.GONE);
            holder.tv_go_pay.setVisibility(View.GONE);
        }

        holder.tv_exit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick()) {

                    if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_NOPAY) {

                        new AlertDialog.Builder(context).setMessage("确认取消此订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onItemClick.exit_order(selfOrderBeans.get(position).getOrderId());
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
        holder.tv_go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick()) {
                    if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_NOPAY) {
                        onItemClick.go_pay(selfOrderBeans.get(position));
                    } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_SHIPPED) {
                        onItemClick.sureReceipt(selfOrderBeans.get(position).getOrderSn());
                    } else if (selfOrderBeans.get(position).getOrderStatus() == MingtaiUtil.ORDER_PAID) {
//                        onItemClick.getQrcode(selfOrderBeans.get(position).getErm());
                    }
                }
            }
        });

        holder.tv_query_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle = new Bundle();
//                bundle.putString("type", "5");
//                bundle.putString("ordersn", selfOrderBeans.get(position).getOrderSn());
//                UiHelper.launcherBundle(context, WebViewActivity.class, bundle);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.tv_order_rv.setLayoutManager(linearLayoutManager);

        SelfOrderListAdapter selfOrderListAdapter = new SelfOrderListAdapter(context, selfOrderBeans.get(position).getList());

        holder.tv_order_rv.setAdapter(selfOrderListAdapter);
        holder.tv_order_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.itemView.performClick();  //模拟父控件的点击
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return selfOrderBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(ArrayList<OrderBean> selfOrderBeans) {
        this.selfOrderBeans = selfOrderBeans;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_order_time;
        private TextView tv_ship_pay;
        private TextView tv_order_amount;
        private RecyclerView tv_order_rv;
        private TextView tv_goods_num;
        private TextView tv_exit_order;
        private TextView tv_go_pay;
        private TextView tv_integral;
        private TextView tv_point_amount;
        private TextView tv_query_logistics;
        private TextView tv_total_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_time = (TextView) itemView.findViewById(R.id.tv_order_time);
            tv_ship_pay = (TextView) itemView.findViewById(R.id.tv_ship_pay);
            tv_order_amount = (TextView) itemView.findViewById(R.id.tv_order_amount);
            tv_order_rv = (RecyclerView) itemView.findViewById(R.id.rv_order_goods);
            tv_goods_num = (TextView) itemView.findViewById(R.id.tv_goods_num);
            tv_exit_order = (TextView) itemView.findViewById(R.id.tv_exit_order);
            tv_go_pay = (TextView) itemView.findViewById(R.id.tv_go_pay);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
            tv_point_amount = (TextView) itemView.findViewById(R.id.tv_point_amount);
            tv_query_logistics = (TextView) itemView.findViewById(R.id.tv_query_logistics);
            tv_total_name = (TextView) itemView.findViewById(R.id.tv_total_name);
        }
    }

    public interface OnItemClick {
        public void exit_order(String orderId);

        public void go_pay(OrderBean orderId);

        public void sureReceipt(String orderId);

        public void cancelOrder(String orderId);

        public void getQrcode(String orderId);
    }
}

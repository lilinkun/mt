package com.mingtai.mt.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.WebviewActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.entity.ProductBean;
import com.mingtai.mt.entity.SelfOrderInfoBean;
import com.mingtai.mt.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<ProductBean> childListBeans;
    private OrderChildAdapter.OnItemClickListener mItemClickListener;

    public OrderProductAdapter(Context context, ArrayList<ProductBean> childListBeans) {
        this.mContext = context;
        this.childListBeans = childListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.child_item, null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.itemView.setTag(position);

        holder.goodsTitle.setText(childListBeans.get(position).getGoodsName());

//        holder.goodsPrice.setText("¥ " + childListBeans.get(position).getPrice());

        holder.goodsnum.setText("X" + childListBeans.get(position).getGoodsNum());

        if (childListBeans.get(position).getLgsName() != null && childListBeans.get(position).getLgsName().trim().length() > 0
        && childListBeans.get(position).getLgsNumber() != null && childListBeans.get(position).getLgsNumber().trim().length() > 0 ) {
            holder.tv_product_LgsNumber.setVisibility(View.VISIBLE);
            holder.tv_shipped.setVisibility(View.VISIBLE);
            holder.tv_product_LgsNumber.setText(childListBeans.get(position).getLgsName() +": " + childListBeans.get(position).getLgsNumber());
        }

        holder.tv_product_LgsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("type", "product");
                bundle.putString("OrderProductId", childListBeans.get(position).getOrderProductId());
                bundle.putString("ordersn", childListBeans.get(position).getOrderSn());
                bundle.putString("CategoryName","物流信息");
                UiHelper.launcherBundle(mContext, WebviewActivity.class, bundle);
            }
        });

        holder.goodsSpec1.setText("产品编号:" + childListBeans.get(position).getGoodsSn());

        Picasso.with(mContext).load(ProApplication.BANNERIMG + childListBeans.get(position).getGoodsImg()).error(R.color.line).into(holder.childImg);
    }

    @Override
    public int getItemCount() {
        return childListBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OrderChildAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView childImg;
        TextView goodsTitle;
        TextView goodsnum;
        TextView goodsPrice;
        TextView goodsSpec1;
        TextView goodsSpec2;
        TextView tv_integral_price;
        TextView tv_product_LgsNumber;
        TextView tv_shipped;
        LinearLayout ll_integral;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsTitle = itemView.findViewById(R.id.tv_goods_title);
            goodsnum = itemView.findViewById(R.id.tv_coupon_price);
            goodsPrice = itemView.findViewById(R.id.tv_goods_price);
            childImg = itemView.findViewById(R.id.iv_goods_pic);
            goodsSpec1 = itemView.findViewById(R.id.tv_goods_spec1);
            goodsSpec2 = itemView.findViewById(R.id.tv_goods_spec2);
            tv_integral_price = itemView.findViewById(R.id.tv_integral_price);
            ll_integral = itemView.findViewById(R.id.ll_integral);
            tv_product_LgsNumber = itemView.findViewById(R.id.tv_product_LgsNumber);
            tv_shipped = itemView.findViewById(R.id.tv_shipped);
        }
    }
}

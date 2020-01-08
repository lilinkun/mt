package com.mingtai.mt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.SelfOrderInfoBean;
import com.mingtai.mt.ui.CustomRoundAngleImageView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/8.
 */
public class SelfOrderListAdapter extends RecyclerView.Adapter<SelfOrderListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SelfOrderInfoBean> selfOrderInfoBeans;

    public SelfOrderListAdapter(Context context, ArrayList<SelfOrderInfoBean> selfOrderInfoBeans) {
        this.context = context;
        this.selfOrderInfoBeans = selfOrderInfoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_goods_count.setText("X" + selfOrderInfoBeans.get(position).getGoodsNumber());
        holder.tv_goods_title.setText("" + selfOrderInfoBeans.get(position).getGoodsName());
        holder.tv_goods_price.setText("¥" + selfOrderInfoBeans.get(position).getPrice());
        holder.tv_goods_market_price.setText(selfOrderInfoBeans.get(position).getMarketPrice()+"");
        holder.tv_goods_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_goods_id.setText("产品编号:" + selfOrderInfoBeans.get(position).getGoodsSn());

        holder.ll_integral.setVisibility(View.VISIBLE);

        if (selfOrderInfoBeans.get(position).getIntegral() == 0) {
            holder.tv_integral.setVisibility(View.GONE);
        } else {
            holder.tv_integral.setText(selfOrderInfoBeans.get(position).getIntegral()+"");
        }

        /*if (selfOrderInfoBeans.get(position).getAttrOne() != null && !selfOrderInfoBeans.get(position).getAttrOne().isEmpty()) {
            holder.tv_goods_spec1.setText(selfOrderInfoBeans.get(position).getAttrOne());
        }

        if (selfOrderInfoBeans.get(position).getAttrTwo() != null && !selfOrderInfoBeans.get(position).getAttrTwo().isEmpty()) {
            holder.tv_goods_spec2.setText(" " + selfOrderInfoBeans.get(position).getAttrTwo());
        }
        Picasso.with(context).load(ProApplication.HEADIMG + selfOrderInfoBeans.get(position).getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.iv_goods_pic);
        */
    }

    @Override
    public int getItemCount() {
        return selfOrderInfoBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_goods_count;
        private TextView tv_goods_title;
        private CustomRoundAngleImageView iv_goods_pic;
        private TextView tv_goods_price;
        private TextView tv_goods_market_price;
        private TextView tv_integral;
        private TextView tv_goods_id;
        private LinearLayout ll_integral;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_count = (TextView) itemView.findViewById(R.id.tv_goods_num);
            tv_goods_title = (TextView) itemView.findViewById(R.id.goods_name);
            iv_goods_pic = (CustomRoundAngleImageView) itemView.findViewById(R.id.goods_image);
            tv_goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            tv_goods_market_price = (TextView) itemView.findViewById(R.id.tv_goods_market_price);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral_price);
            tv_goods_id = (TextView) itemView.findViewById(R.id.tv_goods_id);
            ll_integral = (LinearLayout) itemView.findViewById(R.id.ll_integral);
        }
    }
}

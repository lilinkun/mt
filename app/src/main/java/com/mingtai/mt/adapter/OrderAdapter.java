package com.mingtai.mt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.ChooseItemBean;
import com.mingtai.mt.entity.GoodsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2020/1/6.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GoodsBean> goodsBeans = null;
    private ArrayList<ChooseItemBean> chooseItemBeans = null;

    public OrderAdapter(Context context,ArrayList<GoodsBean> goodsBeans,ArrayList<ChooseItemBean> chooseItemBeans){
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.chooseItemBeans = chooseItemBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order,null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        goodsBeans.get(position);
        holder.tv_goods_id.setText("产品编号:" + goodsBeans.get(position).getGoodsSn());
        holder.goodsName.setText(goodsBeans.get(position).getGoodsName());
        holder.goodsPrice.setText("¥" + goodsBeans.get(position).getPrice() + "");
        holder.tv_goods_num.setText("x"+chooseItemBeans.get(position).getNum());
        holder.tv_goods_market_price.setText(goodsBeans.get(position).getMarketPrice()+"");
        holder.tv_goods_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (goodsBeans.get(position).getIntegral() > 0) {
            holder.ll_integral.setVisibility(View.VISIBLE);
            holder.tv_integral_price.setText(goodsBeans.get(position).getIntegral() + "");
        }
    }

    @Override
    public int getItemCount() {
        return goodsBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.tv_integral_price)
        TextView tv_integral_price;
        @BindView(R.id.tv_goods_id)
        TextView tv_goods_id;
        @BindView(R.id.tv_goods_market_price)
        TextView tv_goods_market_price;
        @BindView(R.id.tv_goods_num)
        TextView tv_goods_num;
        @BindView(R.id.ll_integral)
        LinearLayout ll_integral;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
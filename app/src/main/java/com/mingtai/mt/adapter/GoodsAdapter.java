package com.mingtai.mt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.ChooseItemBean;
import com.mingtai.mt.entity.GoodsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2020/1/3.
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GoodsBean> goodsBeans = null;
    //    private CheckInterface checkInterface;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private int sumGoodsNum = 0;
    private OnItemClickListener mItemClickListener;
    private ArrayList<ChooseItemBean> chooseItemBeans;

    public GoodsAdapter(Context context, ArrayList<GoodsBean> goodsBeans, ArrayList<ChooseItemBean> chooseItemBeans) {
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.chooseItemBeans = chooseItemBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_goods, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.itemView.setTag(position);

        holder.goodsName.setText(goodsBeans.get(position).getGoodsName());
        holder.goodsPrice.setText("¥" + goodsBeans.get(position).getPrice() + "");
        holder.tv_goods_market_price.setText(goodsBeans.get(position).getMarketPrice()+"");
        holder.tv_goods_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.singleCheckBox.setChecked(map.get(goodsBeans.get(position).getCartId()).isChoosed());

        if (goodsBeans.get(position).getIntegral() > 0) {
            holder.ll_integral.setVisibility(View.VISIBLE);
            holder.tv_integral_price.setText(goodsBeans.get(position).getIntegral() + "");
        }

        holder.tv_goods_id.setText("产品编号:" + goodsBeans.get(position).getGoodsSn());

        for (int i = 0; i < chooseItemBeans.size(); i++){
            if (chooseItemBeans.get(i).getGoodsSn().equals(goodsBeans.get(position).getGoodsSn())){
                holder.goodsNum.setText(chooseItemBeans.get(i).getNum() + "");
                holder.singleCheckBox.setChecked(true);
            }
        }

//        Picasso.with(context).load(ProApplication.HEADIMG + orderListBeans.get(position).getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsImage);

        holder.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(holder.goodsNum.getText().toString()) + 1;
                holder.goodsNum.setText(num+"");
                if (holder.singleCheckBox.isChecked()) {
                    checkInterface.checkItem(goodsBeans.get(position).getGoodsSn(), position, Integer.valueOf(holder.goodsNum.getText().toString()));
                }

            }
        });
        holder.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(holder.goodsNum.getText().toString()) - 1;
                if (num != 0) {
                    holder.goodsNum.setText(num + "");
                    if (holder.singleCheckBox.isChecked()) {
                        checkInterface.checkItem(goodsBeans.get(position).getGoodsSn(), position, Integer.valueOf(holder.goodsNum.getText().toString()));
                    }
                }
            }
        });

//        holder.singleCheckBox.setChecked(child.isChoosed());
//        holder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                child.setChoosed(((CheckBox) v).isChecked());
//                holder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
//                checkInterface.checkItem(goodsBeans.get(position).getGoodsSn(),position, Integer.valueOf(holder.goodsNum.getText().toString()));
//            }
//        });

        holder.singleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkInterface.checkItem(goodsBeans.get(position).getGoodsSn(),position, Integer.valueOf(holder.goodsNum.getText().toString()));
                }else{
                    checkInterface.unCheckItem(goodsBeans.get(position).getGoodsSn(),position);
                }
            }
        });
        /*
        holder.goodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(groupPosition,childPosition,childViewHolder.goodsNum,childViewHolder.singleCheckBox.isChecked(),child);
            }
        });*/

        holder.ll_goods_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.singleCheckBox.isChecked()){
                    holder.singleCheckBox.setChecked(false);
                }else {
                    holder.singleCheckBox.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ModifyCountInterface getModifyCountInterface() {
        return modifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public CheckInterface getCheckInterface() {
        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
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


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        void doDecrease(int position, View showCountView, boolean isChecked);

        void doUpdate(int position, View showCountView, boolean isChecked);

        /**
         * 删除子Item
         *
         * @param position
         */
        void childDelete(int position);

        void sumGoodsNum(int num);
    }

    /**
     * 单选框
     */
    public interface CheckInterface {

        /**
         * 选中事件
         *
         * @param position  子元素的位置
         * @param num 子元素的选中与否
         */
        void checkItem(String goodsSn,int position, int num);

        /**
         * 取消事件
         * @param goodsSn
         * @param position
         */
        void unCheckItem(String goodsSn,int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.single_checkBox)
        CheckBox singleCheckBox;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_data)
        LinearLayout goodsData;
        @BindView(R.id.reduce_goodsNum)
        ImageView reduceGoodsNum;
        @BindView(R.id.goods_Num)
        TextView goodsNum;
        @BindView(R.id.increase_goods_Num)
        ImageView increaseGoodsNum;
        @BindView(R.id.ll_integral)
        LinearLayout ll_integral;
        @BindView(R.id.tv_integral_price)
        TextView tv_integral_price;
        @BindView(R.id.tv_goods_id)
        TextView tv_goods_id;
        @BindView(R.id.tv_goods_market_price)
        TextView tv_goods_market_price;
        @BindView(R.id.ll_goods_adapter)
        LinearLayout ll_goods_adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
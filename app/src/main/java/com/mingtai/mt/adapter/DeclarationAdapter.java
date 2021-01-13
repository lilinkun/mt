package com.mingtai.mt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.entity.DeclarationEnum;

/**
 * Created by LG on 2020/1/2.
 */
public class DeclarationAdapter extends RecyclerView.Adapter<DeclarationAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private boolean isDeclaration = false;


    public DeclarationAdapter(Context context){
        this.context = context;

        if (ProApplication.mAccountBean != null) {
            if (ProApplication.mAccountBean.isAgreement() || ProApplication.mAccountBean.getIsSingleCenter() > 0) {
                isDeclaration = true;
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_declaration,null);

        MyViewHolder myViewHolder = new MyViewHolder(view);


        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setTag(position);
        if (!isDeclaration){
            if (position == 0) {
                holder.tv_declaration.setText(DeclarationEnum.SALE.getDeclarationStr());
                holder.iv_declaration.setImageResource(DeclarationEnum.SALE.getImgSrc());
            }else if (position == 1){
                holder.tv_declaration.setText(DeclarationEnum.TIAOBO.getDeclarationStr());
                holder.iv_declaration.setImageResource(DeclarationEnum.TIAOBO.getImgSrc());
            }
        }else {
            holder.tv_declaration.setText(DeclarationEnum.values()[position].getDeclarationStr());
            holder.iv_declaration.setImageResource(DeclarationEnum.values()[position].getImgSrc());
        }

    }

    @Override
    public int getItemCount() {
        return isDeclaration?DeclarationEnum.values().length:DeclarationEnum.values().length-2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_declaration;
        TextView tv_declaration;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_declaration = itemView.findViewById(R.id.iv_declaration);
            tv_declaration = itemView.findViewById(R.id.tv_declaration);
        }
    }
}

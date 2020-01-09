package com.mingtai.mt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.ArticleBean;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/9.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<ArticleBean> articleBeans;
    private OnItemClickListener mItemClickListener;

    public HomeAdapter(Context context, ArrayList<ArticleBean> articleBeans){
        this.context = context;
        this.articleBeans = articleBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home,null);

        view.setOnClickListener(this);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        holder.tv_title.setText(articleBeans.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return articleBeans.size();
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);

        }

    }
}

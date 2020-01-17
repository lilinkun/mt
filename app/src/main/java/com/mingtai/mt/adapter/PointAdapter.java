package com.mingtai.mt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.TiaoboBean;
import com.mingtai.mt.entity.TiaoboHistoryBean;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/17.
 */
public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {

    private Context context;
    private TiaoboHistoryBean tiaoboBeans;

    public PointAdapter(Context context, TiaoboHistoryBean tiaoboBeans){
        this.context = context;
        this.tiaoboBeans = tiaoboBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tiaobo_history,null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.et_point.setText(tiaoboBeans.getHormonicList().get(0).getIntegral() + "");
        holder.et_point.requestLayout();
        holder.et_date.setText(tiaoboBeans.getHormonicList().get(0).getHarmonicDate() + "");
        holder.et_date.requestLayout();
    }

    @Override
    public int getItemCount() {
        return tiaoboBeans.getHormonicList().size()+12;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView et_date;
        TextView et_point;

        public ViewHolder(View itemView) {
            super(itemView);

            et_date = itemView.findViewById(R.id.tv_date);
            et_point = itemView.findViewById(R.id.et_point);
        }
    }
}

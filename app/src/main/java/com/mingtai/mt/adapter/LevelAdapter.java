package com.mingtai.mt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.BankBean;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/3.
 */
public class LevelAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BankBean> bankBeans;

    public LevelAdapter(Context context, ArrayList<BankBean> bankBeans){
        this.mContext = context;
        this.bankBeans = bankBeans;
    }

    @Override
    public int getCount() {
        return bankBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return bankBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_server_local,null);

            viewHolder = new ViewHolder();

            viewHolder.tv_local = (TextView) convertView.findViewById(R.id.tv_local);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_local.setText(bankBeans.get(position).getBankName());

        return convertView;
    }

    class ViewHolder {

        private TextView tv_local;

    }

}

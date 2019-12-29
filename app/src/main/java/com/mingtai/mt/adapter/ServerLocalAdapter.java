package com.mingtai.mt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.entity.LocalBean;

/**
 * Created by lilinkun
 * on 2019/12/29
 */
public class ServerLocalAdapter extends BaseAdapter {

    private Context mContext;

    public ServerLocalAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return LocalBean.values().length;
    }

    @Override
    public Object getItem(int position) {
        return LocalBean.values()[position];
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

        viewHolder.tv_local.setText(LocalBean.values()[position].getLocalStr());

        return convertView;
    }

    class ViewHolder {

        private TextView tv_local;

    }

}

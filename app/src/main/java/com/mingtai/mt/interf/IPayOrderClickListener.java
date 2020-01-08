package com.mingtai.mt.interf;

import com.mingtai.mt.entity.OrderBean;

/**
 * Created by LG on 2020/1/8.
 */
public interface IPayOrderClickListener {
    public void payMode(OrderBean selfOrderBean, int mode);

    public void SureReceive(String orderId);

    public void getQrcode(String orderId);
}
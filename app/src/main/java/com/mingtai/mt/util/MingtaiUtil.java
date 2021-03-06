package com.mingtai.mt.util;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingtai.mt.entity.AccountBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Created by LG on 2019/12/17.
 */
public class MingtaiUtil {

    public static final int PAGE_HOMEPAGE = 0;
    public static final int PAGE_MALL = 1;
    public static final int PAGE_ME = 2;

    public static String RESULT_SUCCESS = "success";
    public static String RESULT_FAIL = "fail";

    public static final String TYPEID = "TYPEID";
    public static final String LOGIN = "login";
    public static final String USERNAME = "username";
    public static final String ORDERSN = "ORDERSN";
    public static final String GOODSTYPE = "goodstype";
    public static final String PRICE = "PRICE";
    public static final String BALANCEBEAN = "BALANCEBEAN";
    public static final String DISCOUNT = "Discount";
    public static final String COIN = "coin";
    public static final String USERBANKBEAN = "UserBankBean";
    public static final String WHERE = "WHERE";

    public static final int UPDATEINT = 2;
    public static final int SALEINT = 4;
    public static final int TIAOBOINT = 8;

//    public static String APP_ID = "wx11a116ef840f67f9";
    public static String APP_ID = "wxb10473f0c1891302";

    //未付款 = 0
    public static final int ORDER_NOPAY = 0;
    //已付款 = 1
    public static final int ORDER_PAID = 1;
    //已发货 = 2
    public static final int ORDER_SHIPPED = 2;
    //交易成功 = 3
    public static final int ORDER_SUCCESSFUL_TRADE  = 3;
    //交易失败 = 4
    public static final int ORDER_FAIL = 4;
    //退单 = 5
    public static final int ORDER_EXIT = 5;
    //冻结 = 6
    public static final int ORDER_FROZEN = 6;
    //部份发货 = 7
    public static final int ORDER_PARTIAL_SHIPMENT = 7;
    //订单取消=8
    public static final int ORDER_CANCEL = 8;




    public static final String VCODE = "";


    public static final String PAGE_COUNT = "20";
    public static final String LOGO_ID = "25";


    public static boolean editIsNotNull(EditText editText){
        if (editText.getText().toString().trim().length() > 0){
            return true;
        }
        return false;
    }

    public static boolean editIsNotNull(TextView editText){
        if (editText.getText().toString().trim().length() > 0){
            return true;
        }
        return false;
    }

    public static String isCoin(double coin){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        String price = numberFormat.format(coin);
        return price;
    }

    public static StringBuilder phoneAddress(String phone) {
        StringBuilder sb = new StringBuilder(phone);
        //取中间四位
        if (sb.length() > 7) {
            sb.replace(3, 7, "****");
        }
        return sb;
    }

    public static void wxPay(String appid, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign, Context context) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(appid);
        try {
//            org.json.JSONObject json = new org.json.JSONObject(result);
//            if (null != json && !json.has("retcode")) {
            PayReq req = new PayReq();
            req.appId = appid;
            req.partnerId = partnerId;
            req.prepayId = prepayId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.packageValue = "Sign=WXPay";
            req.sign = sign;
            req.extData = "app data";
            api.sendReq(req);
//            } else {
//                Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
//                Toast.makeText(this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(context, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 序列化对象
     * @param loginBean
     * @return
     * @throws IOException
     */
    public static String serialize(AccountBean loginBean) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(loginBean);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 序列化对象
     * @return
     * @throws IOException
     */
    public static String serialize(Serializable t) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(t);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化对象
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static AccountBean deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        AccountBean loginBean = (AccountBean) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return loginBean;
    }

    /**
     * 反序列化对象
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Serializable unSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Serializable loginBean = (Serializable) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return loginBean;
    }

}

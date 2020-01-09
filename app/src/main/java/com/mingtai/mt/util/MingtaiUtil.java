package com.mingtai.mt.util;

import android.widget.EditText;
import android.widget.TextView;

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
    public static final String ORDERSN = "ORDERSN";
    public static final String GOODSTYPE = "goodstype";
    public static final String PRICE = "PRICE";
    public static final String BALANCEBEAN = "BALANCEBEAN";
    public static final String DISCOUNT = "Discount";

    public static final int UPDATEINT = 2;
    public static final int SALEINT = 4;
    public static final int TIAOBOINT = 8;

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

}

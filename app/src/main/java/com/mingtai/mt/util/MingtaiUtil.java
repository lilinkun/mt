package com.mingtai.mt.util;

import android.content.Context;

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

    public static String SESSIONID(Context mContext) {
//      return  "wlm06afbb052494856945c98f32d8be2c45";
        return "mt11111111111111111111111111111";
//        return "mt" + DeviceData.getUniqueId(mContext);
    }
}

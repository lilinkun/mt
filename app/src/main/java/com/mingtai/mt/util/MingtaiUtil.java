package com.mingtai.mt.util;

import android.widget.EditText;
import android.widget.TextView;

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

    public static final int UPDATEINT = 2;
    public static final int SALEINT = 4;
    public static final int TIAOBOINT = 8;


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
}

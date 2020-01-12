package com.mingtai.mt.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.util.DeviceData;

/**
 * Created by lilinkun
 * on 2019/12/31
 */
public class ProApplication extends Application {
  private static Context mContext;
  private static ProApplication instance;

  public static AccountBean mAccountBean = null;
  public static HomeBean mHomeBean = null;
  public static String STORENO = null;

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = this;
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    displayMetrics.scaledDensity = displayMetrics.density;

  }



  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
  }

  /**
   * 获得当前app运行的AppContext
   *
   * @return
   */
  public static ProApplication getInstance() {
    return instance;
  }


  public static String SESSIONID(Context mContext) {
//      return  "wlm06afbb052494856945c98f32d8be2c45";
    return "mt" + DeviceData.getUniqueId(mContext);
  }

  public static String BANNERIMG = "";

  public static synchronized ProApplication context() {
    return (ProApplication) mContext;
  }


}
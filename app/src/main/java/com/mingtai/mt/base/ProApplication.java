package com.mingtai.mt.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.util.DeviceData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
    disableAPIDialog();
  }

  /**
   * 反射 禁止弹窗
   */
  private void disableAPIDialog(){
    if (Build.VERSION.SDK_INT < 28)return;
    try {
      Class clazz = Class.forName("android.app.ActivityThread");
      Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
      currentActivityThread.setAccessible(true);
      Object activityThread = currentActivityThread.invoke(null);
      Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
      mHiddenApiWarningShown.setAccessible(true);
      mHiddenApiWarningShown.setBoolean(activityThread, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
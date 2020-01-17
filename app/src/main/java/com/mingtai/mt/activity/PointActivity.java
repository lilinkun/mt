package com.mingtai.mt.activity;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.PointAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.PointContract;
import com.mingtai.mt.entity.TiaoboBean;
import com.mingtai.mt.entity.TiaoboHistoryBean;
import com.mingtai.mt.presenter.PointPresenter;
import com.mingtai.mt.ui.XcyDatePicker;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/17.
 */
public class PointActivity extends BaseActivity implements PointContract {

    @BindView(R.id.rv_tiaobo)
    RecyclerView rv_tiaobo;
    @BindView(R.id.ll_table)
    LinearLayout ll_table;
    @BindView(R.id.tv_have_point)
    TextView tv_have_point;
    @BindView(R.id.tv_surplus_point)
    TextView tv_surplus_point;
    @BindView(R.id.tv_add_tiaobo_item)
    TextView tv_add_tiaobo_item;
    @BindView(R.id.tv_month_point)
    TextView tv_month_point;
    @BindView(R.id.ll_pay_order)
    LinearLayout ll_pay_order;

    private PointPresenter pointPresenter = new PointPresenter();
    private int addInt = 0;
    ArrayList<TiaoboBean> tiaoboBeans = new ArrayList<>();
    ArrayList<Integer> strings = new ArrayList<>();
    HashMap<String,View> stringViewHashMap = new HashMap<>();
    HashMap<String,TextView> stringTextViewHashMap = new HashMap<>();

    private int integral = 0;
    private String orderSn = "";
    private String where =  "";
    private String username = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_point;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));
        pointPresenter.onCreate(this,this);

        integral = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("point");
        orderSn = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.ORDERSN);
        where = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.WHERE);
        username = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.USERNAME);

        pointPresenter.getPointHistory(username, ProApplication.SESSIONID(this));


        tv_have_point.setText(integral+"");
        tv_surplus_point.setText(integral+"");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        rv_tiaobo.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void getPointSuccess(String msg) {

        Bundle bundle = new Bundle();
        bundle.putString(MingtaiUtil.ORDERSN, orderSn);
        bundle.putString(MingtaiUtil.WHERE, where);
        UiHelper.launcherForResultBundle(this, PayActivity.class, 0x0987, bundle);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void getPointFail(String msg) {
        toast(msg);
    }

    @Override
    public void getPointHistorySuccess(TiaoboHistoryBean tiaoboHistoryBean) {
        tv_month_point.setText(tiaoboHistoryBean.getMonthMoney()+"");
        PointAdapter pointAdapter = new PointAdapter(this,tiaoboHistoryBean);
        rv_tiaobo.setAdapter(pointAdapter);
    }

    @Override
    public void getPointHistoryFail(String msg) {

    }

    @OnClick({R.id.tv_add_tiaobo_item,R.id.tv_agree,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_tiaobo_item:
                final PopupWindow popupWindow = new PopupWindow(this);
//                View rootView = LayoutInflater.from(PayActivity.this).inflate(R.layout.pop_date,null);

                final XcyDatePicker xcyDatePicker = new XcyDatePicker(PointActivity.this);

                LinearLayout ll_sure = xcyDatePicker.findViewById(R.id.ll_sure);
                popupWindow.setContentView(xcyDatePicker);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(ll_pay_order);

                ll_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                        View view1 = LayoutInflater.from(PointActivity.this).inflate(R.layout.dialog_date,null);
//                        TextView tv_title = view1.findViewById(R.id.tv_title);
//                        tv_title.setText(year+"年" + (month+1) + "月" + dayOfMonth + "日调拨分值");
                        final EditText editText = view1.findViewById(R.id.et_choose_point);
                        final TextView tv_exit = view1.findViewById(R.id.tv_exit);
                        final TextView tv_sure = view1.findViewById(R.id.tv_sure);

                        final Dialog dialog = new Dialog(PointActivity.this);

                        dialog.setContentView(view1);
                        tv_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        tv_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (editText.getText().toString() != null && !editText.getText().toString().equals("") && editText.getText().toString().trim().length() > 0){
                                    String editStr = editText.getText().toString();
                                    int editInt = Integer.valueOf(editStr);
                                    int surplus = Integer.valueOf(tv_surplus_point.getText().toString());


                                    if (editInt <= surplus){

                                        addInt++;

                                        strings.add(addInt);

                                        tv_surplus_point.setText(surplus-editInt +"");

                                        if (surplus == editInt){
                                            tv_add_tiaobo_item.setVisibility(View.GONE);
                                        }

                                        View view1 = LayoutInflater.from(PointActivity.this).inflate(R.layout.tiaobo_item,null);

                                        EditText tv_date = view1.findViewById(R.id.tv_date);
                                        EditText et_point = view1.findViewById(R.id.et_point);
                                        ImageView tv_reduce = view1.findViewById(R.id.tv_reduce);


                                        TiaoboBean tiaoboBean = new TiaoboBean();
                                        if (xcyDatePicker.getDisplayMonth() < 10){
                                            tiaoboBean.setTime(xcyDatePicker.getDisplayYear() + "-0"+xcyDatePicker.getDisplayMonth());
                                            tv_date.setText(xcyDatePicker.getDisplayYear()+"/0"+xcyDatePicker.getDisplayMonth());
                                        }else {
                                            tiaoboBean.setTime(xcyDatePicker.getDisplayYear() + "-"+xcyDatePicker.getDisplayMonth());
                                            tv_date.setText(xcyDatePicker.getDisplayYear()+"/"+xcyDatePicker.getDisplayMonth());
                                        }
                                        tiaoboBean.setValue(editInt);

                                        tiaoboBeans.add(tiaoboBean);

                                        et_point.setText(editStr);

                                        final int temp = addInt;

                                        stringViewHashMap.put("" + addInt,view1);
                                        stringTextViewHashMap.put(""+addInt,editText);

                                        ll_table.addView(view1);

                                        tv_reduce.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int s = Integer.valueOf(stringTextViewHashMap.get(temp+"").getText().toString());
                                                tv_surplus_point.setText(Integer.valueOf(tv_surplus_point.getText().toString()) + s + "");
                                                ll_table.removeView(stringViewHashMap.get(temp+""));
                                                tv_add_tiaobo_item.setVisibility(View.VISIBLE);
                                                for (int i = 0; i < strings.size();i++){
                                                    if (strings.get(i) == temp){
                                                        tiaoboBeans.remove(i);
                                                    }
                                                }
                                            }
                                        });

                                        dialog.dismiss();
                                    }else {
                                        toast("您的分值不够");
                                    }
                                }
                            }
                        });

                        dialog.show();

                    }
                });

                break;

            case R.id.tv_agree:

                if (tv_surplus_point.getText().toString().equals("0")) {
                    String dateStr = "";
                    String pointStr = "";
                    for (int i = 0; i < tiaoboBeans.size(); i++) {
                        if (i == 0) {
                            dateStr = tiaoboBeans.get(i).getTime();
                            pointStr = tiaoboBeans.get(i).getValue() + "";
                        } else {
                            dateStr = dateStr + "," + tiaoboBeans.get(i).getTime();
                            pointStr = pointStr + "," + tiaoboBeans.get(i).getValue();
                        }
                    }
                    pointPresenter.unloadPoint(orderSn, dateStr, pointStr, ProApplication.SESSIONID(this));
                } else {
                    toast("你输入的调拨值不正确");
                }

                break;

            case R.id.ll_back:

                setResult(RESULT_OK);
                finish();

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
        }


        return super.onKeyDown(keyCode, event);
    }
}

package com.mingtai.mt.activity;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.util.MingtaiUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lilinkun
 * on 2019/12/29
 */
public class SaleActivity extends BaseActivity {

    @BindView(R.id.tv_send_type)
    TextView tv_send_type;
    @BindView(R.id.ll_personal)
    LinearLayout ll_personal;
    @BindView(R.id.ll_business)
    LinearLayout ll_business;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.et_servicer_id)
    TextView et_servicer_id;
    @BindView(R.id.et_servicer_name)
    TextView et_servicer_name;
    @BindView(R.id.et_business_name)
    EditText et_business_name;

    private String send_type_str;
    private int type = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_sale;
    }

    @Override
    public void initEventAndData() {

        int type = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("type");

        if (type == 1){
            tv_detail.setText("保单详情(消费-经销商)");
            et_servicer_id.setText(ProApplication.mAccountBean.getUserName());
            et_servicer_name.setText(ProApplication.mAccountBean.getNickName());
        }else if (type == 2){
            tv_detail.setText("保单详情(升级-经销商)");
        }else if (type == 3){
            tv_detail.setText("保单详情(业绩调拨-经销商)");
            et_servicer_id.setText(ProApplication.mAccountBean.getUserName());
            et_servicer_name.setText(ProApplication.mAccountBean.getNickName());
        }

    }

    @OnClick({R.id.ll_send_type})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_send_type:
                final String[] s = new String[]{"配送到个人","配送到商铺"};

                final PopupWindow popupWindow = new PopupWindow(this);
                View rootView = LayoutInflater.from(this).inflate(R.layout.pop_sale,null,false);
                final NumberPicker numberPicker = rootView.findViewById(R.id.np_sale);
                TextView tv_over = rootView.findViewById(R.id.tv_over);
                numberPicker.setDisplayedValues(s);
                numberPicker.setMaxValue(s.length-1);
                numberPicker.setMinValue(0);

                setType(numberPicker,s);

                //关闭编辑模式
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    //当NunberPicker的值发生改变时，将会激发该方法
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        type = newVal;
                        send_type_str = s[newVal];
                        setType(numberPicker,s);
                    }
                });
                tv_over.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_send_type.setText(send_type_str);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(rootView);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);

                break;
        }
    }

    private void setType(NumberPicker numberPicker,String[] s){
        if (type == 0) {
            ll_personal.setVisibility(View.VISIBLE);
            ll_business.setVisibility(View.GONE);
            send_type_str = s[0];
            numberPicker.setValue(0);
        }else if (type == 1){
            send_type_str = s[1];
            numberPicker.setValue(1);
            ll_business.setVisibility(View.VISIBLE);
            ll_personal.setVisibility(View.GONE);
            et_business_name.setText(ProApplication.mAccountBean.getStoreNo());
        }
    }

}

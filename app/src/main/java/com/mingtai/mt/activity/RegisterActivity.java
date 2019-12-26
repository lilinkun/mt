package com.mingtai.mt.activity;


import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adressselectorlib.AddressPickerView;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.presenter.RegisterPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/12/12.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract {

    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.et_friends_id)
    EditText et_friends_id;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.et_register_name)
    EditText et_register_name;

    private AddressPickerView addressView;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;
    private String mZipCode;
    private ArrayList<EditText> editTexts = new ArrayList<>();
    private boolean isRegister = true;
    RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initEventAndData() {

        registerPresenter.onCreate(this,this);

        setEditString(et_register_name);
        setEditString(et_friends_id);

        et_friends_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    toast(et_friends_id.getText().toString());
                }
            }
        });
    }

    @Override
    public void setDataSuccess(String msg, PageBean pageBean) {

    }

    @Override
    public void setDataFail(String msg) {

    }

    @OnClick({R.id.ll_province})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_province:
                final PopupWindow popupWindow = new PopupWindow(this);
                View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
                addressView = rootView.findViewById(R.id.apvAddress);
                addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
                    @Override
                    public void onSureClick(String address, String provinceCode, String cityCode, String districtCode, String zipCode) {
                        tv_province.setText(address);
                        mProvinceCode = provinceCode;
                        mCityCode = cityCode;
                        mAreaCode = districtCode;
                        mZipCode = zipCode;
                        popupWindow.dismiss();
                    }

                    @Override
                    public void onExit() {
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
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        /*if (imageView != null && imageView.isShown()) {
                            imageView.setVisibility(View.GONE);
                        }*/
                    }
                });
                break;
        }
    }


    String str = "";
    public String setEditString(EditText editString){
        editTexts.add(editString);
        editString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str = s.toString();
                isRegister = true;
                for (int i = 0;i < editTexts.size(); i++){
                    if(editTexts.get(i).getText().toString().trim().length() == 0){
                        isRegister = false;
                    }
                }
                if (isRegister){
                    tv_register.setBackgroundColor(getResources().getColor(R.color.green));
                }else {
                    tv_register.setBackgroundColor(getResources().getColor(R.color.line));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return str;
    }

}

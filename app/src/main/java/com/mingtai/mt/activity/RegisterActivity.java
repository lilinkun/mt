package com.mingtai.mt.activity;


import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.ServerLocalAdapter;
import com.mingtai.mt.adressselectorlib.AddressPickerView;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.LocalBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.presenter.RegisterPresenter;
import com.mingtai.mt.util.MingtaiUtil;

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
    @BindView(R.id.tv_friends_name)
    TextView tv_friends_name;
    @BindView(R.id.ll_local_server)
    LinearLayout ll_local_server;
    @BindView(R.id.ll_server_local)
    LinearLayout ll_server_local;
    @BindView(R.id.tv_server_local)
    TextView tv_server_local;
    @BindView(R.id.et_serverer_id)
    EditText et_serverer_id;
    @BindView(R.id.rl_server_local)
    RelativeLayout rl_server_local;

    private AddressPickerView addressView;
    private String localStr;
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

        et_register_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_register_name.getText().toString().trim().length() > 0){

                    }else {
                        toast("请输入姓名");
                    }
                }
            }
        });

        et_friends_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_friends_id.getText().toString().trim().length() > 0) {
                        registerPresenter.queryName(et_friends_id.getText().toString(), "0", MingtaiUtil.SESSIONID(RegisterActivity.this));
                    }else {
                        toast("请输入亲友人编号");
                    }
                }
            }
        });
    }

    @Override
    public void setDataSuccess(String msg, PageBean pageBean) {

    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void queryNameSuccess(FriendsBean friendsBean,String code) {
        toast(friendsBean.getNickName());
        tv_friends_name.setText(friendsBean.getNickName());
        ll_local_server.setVisibility(View.VISIBLE);
        ll_server_local.setVisibility(View.VISIBLE);

        if (code.equals("-1")){
            tv_server_local.setText(LocalBean.HUALUO.getLocalStr());
            et_serverer_id.setText("");
            et_serverer_id.setClickable(false);
            rl_server_local.setClickable(false);
            registerPresenter.getRefereesName(friendsBean.getNickName(),"0",MingtaiUtil.SESSIONID(RegisterActivity.this));
        }else if (code.equals("0")){
            et_serverer_id.setText(et_friends_id.getText().toString());
            tv_server_local.setText(LocalBean.SERVER.getLocalStr());
            rl_server_local.setClickable(true);
            et_serverer_id.setClickable(true);
        }else {
            rl_server_local.setClickable(true);
            et_serverer_id.setClickable(true);
        }
    }

    @Override
    public void queryNameFail(String msg) {
        toast(msg);
    }

    @Override
    public void getRefereesNameSuccess(FriendsBean friendsBean, String code) {
        et_serverer_id.setText(friendsBean.getNickName());
    }

    @Override
    public void getRefereesNameFail(String msg) {
        toast(msg);
    }

    @OnClick({R.id.ll_province,R.id.rl_server_local})
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

            case R.id.rl_server_local:
                View contentView1 = LayoutInflater.from(this).inflate(R.layout.popup_list, null);

                ListView listView1 = (ListView) contentView1.findViewById(R.id.rv_list);
                listView1.setVisibility(View.VISIBLE);
                listView1.setAdapter(new ServerLocalAdapter(this));

                final PopupWindow popupWindow1 = new PopupWindow(contentView1,
                        LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow1.setContentView(contentView1);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setBackgroundDrawable(new BitmapDrawable());
                popupWindow1.showAsDropDown(ll_server_local);
                popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_server_local.setText(localStr);
                    }
                });
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        localStr = LocalBean.values()[position].getLocalStr();
                        popupWindow1.dismiss();

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

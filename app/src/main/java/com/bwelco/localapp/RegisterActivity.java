package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bwelco.localapp.http.LoginService;
import com.bwelco.localapp.http.NormalResponse;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.register_user)
    EditText user;

    @Bind(R.id.register_password)
    EditText passwd;

    @Bind(R.id.apply_reason)
    EditText applyInfo;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
    }

    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public String toolBarTitle() {
        return "注册";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.register})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.register) {

            if (user.length() == 0) {
                user.setError("请输入账号");
                user.requestFocus();
                return;
            } else if (passwd.length() == 0) {
                passwd.setError("请输入密码");
                passwd.requestFocus();
                return;
            }

            dialog.setMessage("正在注册中");
            dialog.setCancelable(true);
            dialog.show();

            HttpUtil.getRetrofitInstance().create(LoginService.class)
                    .sendRegister(user.getText().toString(), passwd.getText().toString(),
                            applyInfo.getText().toString())
                    .enqueue(new Callback<NormalResponse>() {
                        @Override
                        public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                            ToastUtil.showMessage(response.body().reason);
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<NormalResponse> call, Throwable t) {
                            ToastUtil.showMessage("注册失败");
                            dialog.dismiss();
                            t.printStackTrace();
                        }
                    });
        }
    }
}

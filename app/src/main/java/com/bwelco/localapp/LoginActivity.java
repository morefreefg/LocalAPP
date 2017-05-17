package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bwelco.localapp.utils.KeyBoardManager;
import com.bwelco.localapp.utils.LoginUtil;
import com.bwelco.localapp.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.regist)
    TextView register;


    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login, R.id.regist})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:
                if (phone.length() == 0) {
                    phone.setError("请输入账号");
                    phone.requestFocus();
                    return;
                } else if (password.length() == 0) {
                    password.setError("请输入密码");
                    password.requestFocus();
                    return;
                }

                dialog.setMessage("正在登录中");
                dialog.setCancelable(true);
                dialog.show();
                LoginUtil.sendLogin(phone.getText().toString(),
                        password.getText().toString(), new LoginUtil.LoginCallBack() {

                            @Override
                            public void loginSuccess() {
                                dialog.dismiss();
                                ToastUtil.showMessage("登录成功");
                                LoginUtil.saveUser(LoginActivity.this,
                                        phone.getText().toString(),
                                        password.getText().toString());
                                KeyBoardManager.closeKeyboard(LoginActivity.this);
                            }

                            @Override
                            public void loginFail() {
                                dialog.dismiss();
                                ToastUtil.showMessage("登录失败");
                                KeyBoardManager.closeKeyboard(LoginActivity.this);
                            }
                        });
                break;
            case R.id.regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}

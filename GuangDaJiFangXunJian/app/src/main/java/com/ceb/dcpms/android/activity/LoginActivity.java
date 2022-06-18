package com.ceb.dcpms.android.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.db.DbHandler;
import com.ceb.dcpms.android.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account)
    public EditText etAccount;

    @BindView(R.id.et_password)
    public EditText etPassword;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_login, R.id.btn_face})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:{
                // 登录操作
//                if(etAccount.getText().length() == 0){
//                    handler.showMessage(R.string.err_login_account);
//                    return;
//                }
//                if(etPassword.getText().length() == 0){
//                    handler.showMessage(R.string.err_login_password);
//                    return;
//                }

                //
//                User user = new User();
//                user.setId("1");
//                user.setName("admin");
//
//                DbHandler dbHandler = DbHandler.getInstance(getApplicationContext());
//                if(dbHandler.hasValue(User.Table, User.Id + "=?", new String[]{user.getId()})){
//                    dbHandler.update(user);
//                }else{
//                    dbHandler.save(User.Table, user);
//                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_face:{
                // 人脸识别操作
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}

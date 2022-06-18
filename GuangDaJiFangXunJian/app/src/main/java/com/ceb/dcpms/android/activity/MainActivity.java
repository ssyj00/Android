package com.ceb.dcpms.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.check.server.CheckServerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_account)
    public TextView tvAccount;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_service, R.id.ll_disk, R.id.ll_device, R.id.ll_logout, R.id.tv_download_pic})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_download_pic:{
                // 巡检图片下载
                break;
            }
            case R.id.ll_service:{
                // 服务器巡检
                Intent intent = new Intent(getApplicationContext(), CheckServerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ll_disk:{
                // 硬盘消磁销毁
                break;
            }
            case R.id.ll_device:{
                // 基础设备巡检
                break;
            }
            case R.id.ll_logout:{
                // 退出
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
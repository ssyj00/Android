package com.ceb.dcpms.android.activity.check.server;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.BaseActivity;
import com.ceb.dcpms.android.activity.check.server.fragment.ScheduledTaskFragment;
import com.ceb.dcpms.android.activity.check.server.fragment.TemporaryTaskFragment;
import com.ceb.dcpms.android.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CheckServerActivity extends BaseActivity {

    @BindView(R.id.rl_task_scheduled)
    RelativeLayout rlTaskScheduled;
    @BindView(R.id.rl_task_temporary)
    RelativeLayout rlTaskTemporary;
    @BindView(R.id.vp_container)
    ViewPager2 vpContainer;

    private Unbinder unbinder;
    private List<RelativeLayout> menuList = new ArrayList();
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkserver);

        unbinder = ButterKnife.bind(this);

        menuList.add(rlTaskScheduled);
        menuList.add(rlTaskTemporary);

        fragments.add(new ScheduledTaskFragment());
        fragments.add(new TemporaryTaskFragment());

        adapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        vpContainer.setAdapter(adapter);
        vpContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:{
                        resetMenu(R.id.rl_task_scheduled);
                        break;
                    }
                    case 1:{
                        resetMenu(R.id.rl_task_temporary);
                    }
                }
            }
        });
    }

    @OnClick({R.id.rl_task_scheduled, R.id.rl_task_temporary})
    public void onClick(View v){
        resetMenu(v.getId());
        switch (v.getId()){
            case R.id.rl_task_scheduled:{
                vpContainer.setCurrentItem(0);
                break;
            }
            case R.id.rl_task_temporary:{
                vpContainer.setCurrentItem(1);
                break;
            }
        }
    }

    private void resetMenu(int id){
        for(RelativeLayout tmp : menuList){
            TextView tvName = tmp.findViewById(R.id.tv_name);
            View line = tmp.findViewById(R.id.line);

            if(tmp.getId() == id){
                tvName.setTextAppearance(R.style.TextMenuSelect);
                line.setVisibility(View.VISIBLE);
            }else{
                tvName.setTextAppearance(R.style.TextMenuNormal);
                line.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void back(View v){
        finish();
    }

    @OnClick(R.id.ll_upload)
    public void upload(View v){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();        }
    }
}

package com.ceb.dcpms.android.activity.check.server;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.ceb.dcpms.android.CebApplication;
import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.BaseActivity;
import com.ceb.dcpms.android.activity.photo.PreviewPhotoActivity;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.adapter.check.server.DeviceAttributeAdapter;
import com.ceb.dcpms.android.entity.check.server.Attribute;
import com.ceb.dcpms.android.entity.check.server.Device;
import com.ceb.dcpms.android.loadmore.LoadMoreWrapper;
import com.ceb.dcpms.android.utils.BitmapUtils;
import com.ceb.dcpms.android.utils.GlideRoundTransformation;
import com.ceb.dcpms.android.utils.ImageUtils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.swiperefresh)
//    SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.ll_photo_list)
    LinearLayout llPhotoList;

    private Unbinder unbinder;
    private DeviceAttributeAdapter adapter;

    private RxPermissions rxPermissions;
    private CebApplication app;

    private List<String> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkserver_devicedetail);

        unbinder = ButterKnife.bind(this);

        rxPermissions = new RxPermissions(this);
        app = (CebApplication) getApplication();

        adapter = new DeviceAttributeAdapter();
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Attribute>() {
            @Override
            public void onItemClick(View view, Attribute attribute) {

            }
        });

//        mySwipeRefreshLayout.setColorSchemeColors(
//                getResources().getColor(R.color.colorSwipeRefreshLayout1),
//                getResources().getColor(R.color.colorSwipeRefreshLayout2),
//                getResources().getColor(R.color.colorSwipeRefreshLayout3)
//        );
//
//        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                doQuery();
//            }
//        });
//
//        mySwipeRefreshLayout.post(()->{
//            mySwipeRefreshLayout.setRefreshing(true);
//            doQuery();
//        });
        doQuery();
    }

    private void doQuery(){
        for(int i=0; i<5; i++){
            adapter.add(new Attribute());
        }
//        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_save, R.id.ll_prev, R.id.ll_next, R.id.iv_add})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:{
                finish();
                break;
            }
            case R.id.iv_add:{
                rxPermissions.requestEachCombined(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA})
                        .subscribe(permission -> {
                            if(permission.granted){
                                File path = new File(Constants.Path.imgPath);
                                if(!path.exists())
                                    path.mkdirs();

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                                String fileName = sdf.format(new Date(System.currentTimeMillis())) + ".jpg";

                                app.setFileName(Constants.Path.imgPath + fileName);

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.Path.imgPath + fileName)));

                                if(Build.VERSION.SDK_INT >= 24){
                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());
                                    builder.detectFileUriExposure();
                                }

                                startActivityForResult(intent, Constants.Request.CameraWithData);
                            }else if(permission.shouldShowRequestPermissionRationale){
                                showMessage(R.string.rationale_ask_again);
                            }else{
                                showMessage(R.string.rationale_cancle);
                            }});
                break;
            }
            case R.id.ll_save:{
                break;
            }
            case R.id.ll_prev:{
                break;
            }
            case R.id.ll_next:{
                break;
            }
        }
    }

    private void addPhoto(String fileName){
        View view = getLayoutInflater().inflate(R.layout.adapter_photo, null);
        ImageView ivPhoto = view.findViewById(R.id.iv_photo);
        Glide.with(this)
                .load(fileName)
                .fitCenter()
                .into(ivPhoto);

        view.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPhotoList.removeView(view);
                photos.remove(fileName);
                File file = new File(fileName);
                file.delete();
            }
        });

        llPhotoList.addView(view, llPhotoList.getChildCount() - 1);
        photos.add(fileName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreviewPhotoActivity.class);;
                intent.putExtra(Constants.Tag.data, fileName);
                startActivity(intent);
            }
        });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.Request.CameraWithData && resultCode == RESULT_OK){
            try {
                Bitmap photo = ImageUtils.decodeStream(this, Uri.fromFile(new File(app.getFileName())));

                // 处理图片旋转问题
                int degree = BitmapUtils.readPictureDegree(app.getFileName());
                photo = BitmapUtils.rotaingImageView(degree, photo);

                // 压缩
                int width = 0;
                int height = 0;
                int desWidth = 1024;
                if(photo.getWidth() > desWidth || photo.getHeight() > desWidth){
                    if(photo.getWidth() > desWidth){
                        width = desWidth;
                        float scale = (float) photo.getWidth() / (float) photo.getHeight();
                        height = (int) (width / scale);
                    }else if(photo.getHeight() > desWidth){
                        height = desWidth;
                        float scale = (float) photo.getWidth() / (float) photo.getHeight();
                        width = (int) (height * scale);
                    }

                    photo = Bitmap.createScaledBitmap(photo, width, height, true);
                }

                FileOutputStream fos = new FileOutputStream(app.getFileName());
                photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                addPhoto(app.getFileName());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

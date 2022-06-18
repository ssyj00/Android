package com.ceb.dcpms.android.activity.video;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.manager.camera.CameraHandler;
import com.ceb.dcpms.android.manager.camera.CameraManager;
import com.ceb.dcpms.android.utils.GID;
// 视频拍摄类，生成的文件为3gp格式的视频文件，如果需要转换为mp4格式，参考下面的连接进行修改
// https://blog.csdn.net/A1735a/article/details/121874326
public class RecorderVideoActivity extends Activity implements OnClickListener,
		SurfaceHolder.Callback {

	private SurfaceView surfaceview;
	private Button start, save;
	private MediaRecorder mediarecorder;
	private SurfaceHolder surfaceHolder;

	private boolean isStart = false;
	private boolean hasSurface = false;
	private CameraHandler handler;
	
	private ProgressBar timerBar;
	private CountDownTimer timer;
	
	private String fileName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.activity_record_video);

		CameraManager.init(getApplicationContext());
		
		start = (Button) findViewById(R.id.btn_start);
		start.setOnClickListener(this);
		
		save = (Button) findViewById(R.id.btn_save);
		save.setOnClickListener(this);
		save.setEnabled(false);

		surfaceview = (SurfaceView) findViewById(R.id.surfaceView);
		SurfaceHolder holder = surfaceview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		timerBar = (ProgressBar) findViewById(R.id.progressBar);
		
		File path = new File(Constants.Path.videoPath);
		if(!path.exists())
			path.mkdirs();
		
		fileName = Constants.Path.videoPath + GID.gid() + ".3gp";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start: {
			start.setClickable(false);
			if (!isStart) {
				start.setText("停止");
				startRecord();
				
				timerBar.setProgress(0);
				timerBar.setMax(30000);
				timer = new CountDownTimer(30000, 1) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						timerBar.setProgress((int) (30000 - millisUntilFinished));
					}
					
					@Override
					public void onFinish() {
						stopRecord();
						start.setText("开始");
						save.setEnabled(true);
						Log.i(Constants.Log.Log_Tag, "onFinish");
					}
				};
				timer.start();
				save.setEnabled(false);
			} else {
				timer.cancel();
				start.setText("开始");
				stopRecord();
				save.setEnabled(true);
			}
			start.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					start.setClickable(true);					
				}
			}, 5000);
			break;
		}
		case R.id.btn_save:{
			Intent intent = new Intent();
			intent.putExtra(Constants.Tag.FILELIST, fileName);
			setResult(Constants.Result.VideoCapture, intent);
			finish();
			break;
		}
		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(!isStart){
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			if(hasSurface){
				initCamera(surfaceHolder);
			}else{
				surfaceHolder.addCallback(this);
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			}
		}
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		if(!isStart){
			if(handler != null){
				handler.quitSynchronously();
				handler = null;
			}
			CameraManager.get().closeDriver();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		if(isStart){
			if(timer != null){
				timer.cancel();
				start.setText("开始");
				stopRecord();
				save.setEnabled(true);
			}
			return;
		}
		super.onBackPressed();
	}

	private void startRecord(){
		CameraManager.get().stopPreview();
		CameraManager.get().closeDriver();
		
		mediarecorder = new MediaRecorder();// 创建mediarecorder对象
		// 设置录制视频源为Camera(相机)
		mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
		mediarecorder
				.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		// 设置录制的视频编码h263 h264
		mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
		mediarecorder.setVideoSize(320, 240);
		// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
		mediarecorder.setVideoFrameRate(20);
		
		mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
		// 设置视频文件输出的路径
		mediarecorder.setOutputFile(fileName);
		try {
			// 准备录制
			mediarecorder.prepare();
			// 开始录制
			mediarecorder.start();

			isStart = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void stopRecord(){
		if (mediarecorder != null) {
			// 停止录制
			mediarecorder.stop();
			// 释放资源
			mediarecorder.release();
			mediarecorder = null;
		}
		isStart = false;
	
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CameraManager.get().startPreview();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		surfaceHolder = holder;
		if(!hasSurface){
			hasSurface = true;
			initCamera(surfaceHolder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
		
		surfaceHolder = null;
		surfaceview = null;
		mediarecorder = null;
		
		Log.i(Constants.Log.Log_Tag, ":)");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		CameraManager.get().closeDriver();
	}

	private void startCameraPreview(){
		if(CameraManager.get() == null)
			return;
		
		CameraManager.get().startPreview();
		
		if(handler != null){
			Message message = handler.obtainMessage(R.id.auto_focus);
			handler.sendMessage(message);
		}
		
		Log.i(Constants.Log.Log_Tag, "startCameraPreview");
	}
	
	private void initCamera(SurfaceHolder surfaceHolder){
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e){
			e.printStackTrace();
			return;
		}
		
		if(handler == null)
			handler = new CameraHandler();
		
		startCameraPreview();
	}

}

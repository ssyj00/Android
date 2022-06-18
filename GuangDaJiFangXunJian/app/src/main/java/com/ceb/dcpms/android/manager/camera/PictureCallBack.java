package com.ceb.dcpms.android.manager.camera;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;

/**
 * <p>PictureCallBack.java</p>
 * <p>图片保存回调类</p>
 *
 * @author		孙广智(tony.u.sun@163.com)
 * @version		0.0.1
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.1</td><td>创建类</td><td>sunguangzhi</td><td>2014-12-26 下午3:14:56</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public class PictureCallBack implements PictureCallback {

	private Activity activity;
	private OnPictureCallBackListener listener;
	
	public PictureCallBack(Activity activity){
		this.activity = activity;
	}
	
	public void setOnPictureCallBackListener(OnPictureCallBackListener listener){
		this.listener = listener;
	}
	
	public void setError(){
//		if(listener != null)
//			listener.onError();
	}
	
	@Override
	public void onPictureTaken(final byte[] data, final Camera camera) {
		if(listener != null)
			listener.onReceived(data);
		
	}

	/**
	 * <p>PictureCallBack.OnPictureCallBackListener</p>
	 * <p>图片保存回调接口</p>
	 *
	 * @author		孙广智(tony.u.sun@163.com)
	 * @version		0.0.1
	 * <table style="border:1px solid gray;">
	 * <tr>
	 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
	 * </tr>
	 * <!-- 以 Table 方式书写修改历史 -->
	 * <tr>
	 * <td>0.0.1</td><td>创建类</td><td>sunguangzhi</td><td>2014-12-26 下午3:15:11</td>
	 * </tr>
	 * <tr>
	 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
	 * </tr>
	 * </table>
	*/
	public interface OnPictureCallBackListener {
		/**
		 * 图片保存成功
		 * @param fileName
		 */
		public void onSave(String fileName);
		/**
		 * 接收到数据时调用
		 * @param data
		 */
		public void onReceived(byte[] data);
		/**
		 * 发送错误
		 */
		public void onError();
	}
}

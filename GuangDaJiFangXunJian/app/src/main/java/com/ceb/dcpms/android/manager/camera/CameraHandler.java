package com.ceb.dcpms.android.manager.camera;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ceb.dcpms.android.R;

/**
 * <p>CameraHandler.java</p>
 * <p>照相管理</p>
 *
 * @author		孙广智(tony.u.sun@163.com)
 * @version		0.0.1
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.1</td><td>创建类</td><td>sunguangzhi</td><td>2014-12-26 下午3:01:28</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public final class CameraHandler extends Handler {

	private static final String TAG = CameraHandler.class.getSimpleName();

	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CameraHandler() {
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {

		if (message.what == R.id.auto_focus) {
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}

		} else if (message.what == R.id.restart_preview) {
			Log.d(TAG, "Got restart preview message");

			restartPreviewAndDecode();

		} else if (message.what == R.id.decode_succeeded) {

			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;

		} else if (message.what == R.id.decode_failed) {
			state = State.PREVIEW;
		} else if (message.what == R.id.return_scan_result) {
		} else if (message.what == R.id.launch_product_query) {
			Log.d(TAG, "Got product query message");
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
		}
	}

}

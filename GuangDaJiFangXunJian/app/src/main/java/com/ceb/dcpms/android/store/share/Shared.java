package com.ceb.dcpms.android.store.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <p>Shared</p>
 * <p>SharedPreferences 基础操作</p>
 *
 * @author		孙广智(tony.u.sun@163.com;sunguangzhi@nvlbs.com)
 * @version		0.0.0
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.0</td><td>创建类</td><td>sunguangzhi</td><td>2013-6-7 下午04:42:18</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public class Shared {

	public static void put(Context context, String key, String value){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void put(Context context, String key, boolean value){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void put(Context context, String key, float value){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public static void put(Context context, String key, int value){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void put(Context context, String key, long value){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static String getString(Context context, String key){
		SharedPreferences sp = getShared(context);
		return sp.getString(key, "");
	}
	
	public static boolean getBoolean(Context context, String key){
		SharedPreferences sp = getShared(context);
		return sp.getBoolean(key, false);
	}
	
	public static int getInt(Context context, String key){
		SharedPreferences sp = getShared(context);
		return sp.getInt(key, 0);
	}
	
	public static long getLong(Context context, String key){
		SharedPreferences sp = getShared(context);
		return sp.getLong(key, 0l);
	}
	
	public static float getFloat(Context context, String key){
		SharedPreferences sp = getShared(context);
		return sp.getFloat(key, 0.0f);
	}
	
	public static void clear(Context context){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void remove(Context context, String key){
		SharedPreferences sp = getShared(context);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}
	
	private static SharedPreferences getShared(Context c){
		SharedPreferences sp = c.getSharedPreferences(c.getPackageName(), Context.MODE_PRIVATE);
		return sp;
	}
}

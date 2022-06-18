package com.ceb.dcpms.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.entity.User;
import com.ceb.dcpms.android.store.db.AbstractDbHandler;

import java.lang.reflect.InvocationTargetException;

public class DbHandler extends AbstractDbHandler {

	public static final Object lock = new Object();

	private static final String name = "dcpms.db";

	private static final int version = 171;

	private DbHelper dbHelper;

	private static DbHandler dbHandler;

	private Context context;

	private DbHandler(Context context){
		dbHelper = new DbHelper(context, name, null, version);
		this.context = context;
	}

	public static DbHandler getInstance(Context context){
		if(dbHandler == null){
			synchronized(DbHandler.class){
				if(dbHandler == null){
					dbHandler = new DbHandler(context);
				}
			}
		}
		return dbHandler;
	}

	public void sayByeBye(){
		if(dbHelper != null)
			dbHelper.close();
		dbHelper = null;
		dbHandler = null;
	}

	public boolean hasValue(String table, String where, String[] selectionArgs){
		synchronized (lock) {
			String sql = "select * from " + table + " where " + where;
			Log.i(Constants.Log.Log_Tag, sql);
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery(sql, selectionArgs);
			boolean has = false;
			if(c.moveToFirst())
				has = true;
			c.close();
			db.close();
			return has;
		}
	}

	// 数据更新，参考
	public void update(User obj){
		synchronized (lock) {
			try {
				ContentValues values = getContentValues(obj);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.update(User.Table, values, User.Id + "=?", new String[]{obj.getId()});
				db.close();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}


	// 数据查询，参考
//	private int getBingHaiXinXiUpdatetimes(String id){
//		synchronized (lock) {
//			String sql = "select "+BingHaiXinXi.Updatetimes+" from " + BingHaiXinXi.Table + " where " + BingHaiXinXi.ID + "=?";
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			Cursor c = db.rawQuery(sql, new String[]{id});
//			int updatetimes = 0;
//			if(c.moveToFirst())
//				updatetimes =  c.getInt(c.getColumnIndex(BingHaiXinXi.Updatetimes));
//			c.close();
//			db.close();
//			return updatetimes;
//		}
//	}


	// 数据保存
	public void save(String table, Object obj){
		synchronized (lock) {
			try {
				ContentValues values = getContentValues(obj);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				long ret = db.insert(table, null, values);
				db.close();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}


	// 清空表数据
	@Override
	public void clear(String table) {
		synchronized (lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.delete(table, null, null);
			db.close();
		}
	}

	// 获取列表数据
//	public List<User> listUser(){
//		synchronized (lock) {
//			String sql = "select * from " + User.Table + " order by " + User.LastLoginTime + " desc";
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			Cursor c = db.rawQuery(sql, null);
//			List<User> user = new ArrayList<User>();
//			while(c.moveToNext())
//				user.add((User) getEntity(c, User.class));
//			c.close();
//			db.close();
//			return user;
//		}
//	}

}
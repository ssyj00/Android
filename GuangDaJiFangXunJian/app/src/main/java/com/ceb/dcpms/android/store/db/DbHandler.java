package com.ceb.dcpms.android.store.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler<T> {

	private Class<T> t;
	private SQLiteDatabase db;
	
	public DbHandler(Context context, Class<T> t, String dbName, int version){
		this.t = t;
		
		SQLiteOpenHelper helper = new DbHelper(context, dbName, null, version);
		
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		db = helper.getWritableDatabase();
		
		int spVersion = sp.getInt(getTableName() + "DbVersion", 0);
		
		if(spVersion != version){
			helper.onUpgrade(db, spVersion, version);
		}
		Editor editor = sp.edit();
		editor.putInt(getTableName() + "DbVersion", version);
		editor.commit();
	}
	
	public void close(){
		if(db != null){
			db.close();
			db = null;
		}
	}
	
	public SQLiteDatabase getDb(){
		return db;
	}
	
	private class DbHelper extends SQLiteOpenHelper {
		
		public DbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Field[] fields = t.getDeclaredFields();

			String sql = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (";
			for(int i=0; i<fields.length; i++){
				int mod = fields[i].getModifiers();
				if(Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isPublic(mod))
					continue;
				
				String typeName = getFieldType(fields[i].getType().getName().toLowerCase());
				String fieldName = fields[i].getName();
				
				if(typeName.equals("string")){
					sql += fieldName.toLowerCase() + " varchar2(50), ";
				}else if(typeName.equals("float")){
					sql += fieldName.toLowerCase() + " float, ";
				}else if(typeName.equals("int")){
					sql += fieldName.toLowerCase() + " integer, ";
				}else if(typeName.equals("boolean")){
					sql += fieldName.toLowerCase() + " integer, ";
				}else if(typeName.equals("long")){
					sql += fieldName.toLowerCase() + " long, ";
				}else if(typeName.equals("double")){
					sql += fieldName.toLowerCase() + " float, ";
				}else if(typeName.equals("short")){
					sql += fieldName.toLowerCase() + " short, ";
				}
			}
			
			if(sql.lastIndexOf(',') >= 0)
				sql = sql.substring(0, sql.lastIndexOf(','));
			sql = sql + ")";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + getTableName());
			onCreate(db);
		}
		
	}
	
	public String getTableName(){
		String name = t.getName();
		if(name.lastIndexOf('.') >= 0)
			name = name.substring(name.lastIndexOf('.') + 1);
		
		return name;
	}
	
	private String getFieldType(String type){
		if(type.lastIndexOf('.') >= 0)
			type = type.substring(type.lastIndexOf('.') + 1);
		return type;
	}
	
	public void save(T t){
		try {
			db.insert(getTableName(), null, getContentValues(t));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public ContentValues getContentValues(T t) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Field[] declaredFields = this.t.getDeclaredFields();
		List<String> fields = new ArrayList<String>();
		for(Field field : declaredFields)
			fields.add(field.getName().toLowerCase());
		
		ContentValues values = new ContentValues();
		Method[] methods = this.t.getMethods();
		for(Method method : methods){
			String methodName = method.getName();
			if(methodName.startsWith("get")){
				String filedName = methodName.substring(3, methodName.length());
				if(fields.contains(filedName.toLowerCase())){
					String typeName = getFieldType(method.getReturnType().getName().toLowerCase());
					System.out.println(methodName + " " + typeName + " " + method.invoke(t));
					if(typeName.equals("string")){
						values.put(filedName, (String)method.invoke(t));
					}else if(typeName.equals("float")){
						values.put(filedName, (Float)method.invoke(t));
					}else if(typeName.equals("int")){
						values.put(filedName, (Integer)method.invoke(t));
					}else if(typeName.equals("boolean")){
						values.put(filedName, ((Boolean)method.invoke(t)) ? 1 : 0);
					}else if(typeName.equals("long")){
						values.put(filedName, (Long)method.invoke(t));
					}else if(typeName.equals("double")){
						values.put(filedName, (Double)method.invoke(t));
					}else if(typeName.equals("short")){
						values.put(filedName, (Short)method.invoke(t));
					}
				}
			}
		}
		
		return values;
	}
	
	public List<T> list(int page, int rows){
		String sql = "select * from " + getTableName() + " limit " + rows + " offset " + ((page - 1) * rows);
		List<T> objets = new ArrayList<T>();
		
		Cursor c = db.rawQuery(sql, null);
		while(c.moveToNext()){
			T t = getEntity(c);
			if(t != null)
				objets.add(t);
		}
		c.close();
		
		return objets;
	}
	
	public T listById(int id){
		String sql = "select * from " + getTableName() + " where id=" + id;
		Cursor c = db.rawQuery(sql, null);
		T t = null;
		if(c.moveToNext())
			t = getEntity(c);
		c.close();
		return t;
	}
	
	public void update(T t){
		try {
			int id = -1;
			Field[] declaredFields = this.t.getDeclaredFields();
			List<String> fields = new ArrayList<String>();
			for(Field field : declaredFields)
				fields.add(field.getName().toLowerCase());
			
			ContentValues values = new ContentValues();
			Method[] methods = this.t.getMethods();
			for(Method method : methods){
				String methodName = method.getName();
				if(methodName.startsWith("get")){
					String filedName = methodName.substring(3, methodName.length());
					if(fields.contains(filedName.toLowerCase())){
						String typeName = getFieldType(method.getReturnType().getName().toLowerCase());

						if(typeName.equals("string")){
							values.put(filedName, (String)method.invoke(t));
						}else if(typeName.equals("float")){
							values.put(filedName, (Float)method.invoke(t));
						}else if(typeName.equals("int")){
							if(filedName.toLowerCase().equals("id"))
								id = (Integer)method.invoke(t);
							values.put(filedName, (Integer)method.invoke(t));
						}else if(typeName.equals("boolean")){
							values.put(filedName, ((Boolean)method.invoke(t)) ? 1 : 0);
						}else if(typeName.equals("long")){
							values.put(filedName, (Long)method.invoke(t));
						}else if(typeName.equals("double")){
							values.put(filedName, (Double)method.invoke(t));
						}else if(typeName.equals("short")){
							values.put(filedName, (Short)method.invoke(t));
						}
					}
				}
			}

			db.update(getTableName(), values, "id=" + id, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id){
		db.delete(getTableName(), "id=" + id, null);
	}
	
	public void clear(){
		db.delete(getTableName(), null, null);
	}
	
	public int getCount(){
		String sql = "select count(*) as c from " + getTableName();
		Cursor c = db.rawQuery(sql, null);
		int count = 0;
		if(c.moveToNext())
			count = c.getInt(c.getColumnIndex("c"));
		c.close();
		return count;
	}
	
	public T getEntity(Cursor c){
		try {
			Object obj = t.newInstance();
			
			Field[] declaredFields = t.getDeclaredFields();
			List<String> fields = new ArrayList<String>();
			for(Field field : declaredFields)
				fields.add(field.getName().toLowerCase());
			
			Method[] methods = t.getMethods();
			for(Method method : methods){
				String methodName = method.getName();
				if(methodName.startsWith("set")){
					String filedName = methodName.substring(3, methodName.length());
					if(fields.contains(filedName.toLowerCase())){
						String typeName = getFieldType(method.getParameterTypes()[0].getName().toLowerCase());

						if(typeName.equals("string")){
							method.invoke(obj, c.getString(c.getColumnIndex(filedName.toLowerCase())));
						}else if(typeName.equals("float")){
							method.invoke(obj, c.getFloat(c.getColumnIndex(filedName.toLowerCase())));
						}else if(typeName.equals("int")){
							method.invoke(obj, c.getInt(c.getColumnIndex(filedName.toLowerCase())));
						}else if(typeName.equals("boolean")){
							method.invoke(obj, c.getInt(c.getColumnIndex(filedName.toLowerCase())) == 1);
						}else if(typeName.equals("long")){
							method.invoke(obj, c.getLong(c.getColumnIndex(filedName.toLowerCase())));
						}else if(typeName.equals("double")){
							method.invoke(obj, c.getDouble(c.getColumnIndex(filedName.toLowerCase())));
						}else if(typeName.equals("short")){
							method.invoke(obj, c.getShort(c.getColumnIndex(filedName.toLowerCase())));
						}
					}
				}
			}

			return (T) obj;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

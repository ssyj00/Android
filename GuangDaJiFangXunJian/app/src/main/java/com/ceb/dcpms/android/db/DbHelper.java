package com.ceb.dcpms.android.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.ceb.dcpms.android.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SDCardSQLiteOpenHelper {

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 数据库初始化时，创建表的操作
		db.execSQL("CREATE TABLE IF NOT EXISTS " + User.Table + " (" +
				User.Id + " varchar2(200), " +
				User.Name + " varchar2(200) " +
				")");

		// 数据库更新，不删除表，增加字段时使用下面的操作进行字段的添加操作
//		Cursor c_Book = db.rawQuery("select * from sqlite_master where name='Book'", new String[]{});
//		if(c_Book.moveToFirst()){
//			String[] fields = {
//					"pmzxzh", "lmzyc", "sbjgz"
//			};
//
//			for(String field : fields){
//				Cursor cursor = db.rawQuery("select * from sqlite_master where name='Book' and sql like '%" + field+ "%'", new String[]{});
//				if(!cursor.moveToFirst()){
//					db.execSQL("ALTER TABLE Book ADD COLUMN " + field + " text");
//				}
//			}
//		}

	}

	private boolean hasField(List<String> fieldsList, String field){
		boolean hasField = false;
		for(String tmp : fieldsList){
			if(field.equals(tmp)){
				hasField = true;
				break;
			}
		}
		return hasField;
	}

	private List<String> getFields(String sql){
		int start = sql.indexOf("(");
		int end = sql.lastIndexOf(")");
		List<String> fieldList = new ArrayList<>();
		if(start > 0 && end >0 && end > start){
			sql = sql.substring(start+1, end);
			String[] fields = sql.split(",");
			for(String field : fields){

				String name = field.trim().split(" ")[0];
				if(name.startsWith("\"")){
					name = name.substring(1, name.length()-1);
				}else if(name.startsWith("[")){
					name = name.substring(1, name.length()-1);
				}else if(name.startsWith("'")){
					name = name.substring(1, name.length()-1);
				}
				fieldList.add(name);
			}
		}
		return fieldList;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 数据库更新时，如果需要删除表，则调用表删除操作
//		db.execSQL("DROP TABLE IF EXISTS " + User.Table);
		
		onCreate(db);
	}
	
}

package com.ceb.dcpms.android;

import android.content.Context;
import android.os.Environment;

import com.ceb.dcpms.android.db.DbHandler;
import com.ceb.dcpms.android.store.share.Shared;
import com.ceb.dcpms.android.utils.StringUtils;

import java.io.File;

public class Constants {

	public static Object dbLock = new Object();

//	public static User getUser(Context ctx){
//		DbHandler dbHandler = DbHandler.getInstance(ctx);
//		User user = dbHandler.getUser(Shared.getString(ctx, Tag.lastLoginUserId));
//		return user;
//	}

	public static class Log {
		public static final String Log_Tag = "cebLog";
	}

	public static class Path {
		public static final String basePath = Environment.getExternalStorageDirectory().toString() + File.separator + "com.ceb.dcpms.android" + File.separator;
		public static final String imgPath = basePath + "img" + File.separator;
		public static final String soundPath = basePath + "sound" + File.separator;
		public static final String databasePath = "db";
		public static final String videoPath = basePath + "video" + File.separator;
	}
	
	public static class Request {
		public static final int CameraWithData = 101;
		public static final int PhotoPickedWithData = 102;
		public static final int VideoCapture = 103;
	}

	public static class Result {
		public static final int VideoCapture = 203;
	}

	public static class Action {

	}

	public static class Tag {
		public static final String FILELIST = "FILELIST";
		public static final String data = "data";
		public static final String type = "type";
	}

	public static class Type {
		public static class Task {
			public static final int scheduled = 1;
			public static final int temporary = 2;
		}
	}
	
	public static class Code {

	}
	
}

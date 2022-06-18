package com.ceb.dcpms.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ceb.dcpms.android.MessageHandler;

public class BaseActivity extends AppCompatActivity {
	
	public MessageHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new MessageHandler(this);
	}

	protected void showMessage(String str){
		handler.showMessage(str);
	}

	protected void showMessage(int resId){
		handler.showMessage(resId);
	}

}

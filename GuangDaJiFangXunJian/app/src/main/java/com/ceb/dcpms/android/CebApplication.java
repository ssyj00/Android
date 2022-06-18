package com.ceb.dcpms.android;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class CebApplication extends Application {

    private String LOGGER_TAG = "cebLogger";
    private String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAndroidLoggerUtil();
    }

    private void initAndroidLoggerUtil() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(2)      // (Optional) How many method line to show
                .methodOffset(5)    // (Optional) Hides internal method calls up to offset.
                .tag(LOGGER_TAG)   // (Optional) Global tag for every log
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;//Debug模式才可用
            }
        });
        FormatStrategy csvStrategy = CsvFormatStrategy.newBuilder()
                .tag(LOGGER_TAG)  //设置自定义tag，方便过滤筛选
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(csvStrategy));
    }
}

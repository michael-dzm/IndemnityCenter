package com.sh3h.indemnity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.sh3h.indemnity.injection.component.ApplicationComponent;
import com.sh3h.indemnity.injection.component.DaggerApplicationComponent;
import com.sh3h.indemnity.injection.module.ApplicationModule;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.Date;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    public static final String MAP_PACKAGE_NAME = "com.sh3h.citymap";
    public static final String MAP_AIDL_SERVICE_NAME = "com.sh3h.citymap.service.MapAidlService";
    private static final String PACKAGE_BASE_NAME = "com.sh3h";

    private ApplicationComponent mApplicationComponent;

    private long timeError;

    public MainApplication() {
        mApplicationComponent = null;
        timeError = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "--onCreate--");
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        IMainServiceManager.getInstance(this).bindService();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "--onTerminate--");
        //程序终止
        IMainServiceManager.getInstance(this).bindService();
        LogUtil.closeLogger();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "--onLowMemory--");
        //低内存
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "--onTrimMemory--");
        //内存清理
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public void setTimeError(long timeError) {
        this.timeError = timeError;
    }

    public long getCurrentTime() {
        long time = new Date().getTime();
        return time + timeError;
    }

    public Date getCurrentDate() {
        return new Date(getCurrentTime());
    }

}

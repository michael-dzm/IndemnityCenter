package com.sh3h.indemnity.service.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.sh3h.ipc.IMainService;
import com.sh3h.ipc.IRemoteServiceCallback;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

/**
 * Created by dengzhimin on 2017/5/9.
 * AIDL通信管理类
 * {@link #bindService() 绑定服务
 *  @link #unbindService() 解绑服务}
 *
 */

public class IMainServiceManager {

    private static final String TAG = IMainServiceManager.class.getSimpleName();
    private static final String HOST_SERVICE_NAME = "com.sh3h.mainshell.service.HostService";
    public static final String BINDING_NAME = "bindingName";

    private Context context;
    private IMainService iMainService;
    private static IMainServiceManager iMainManager;

    private MyModule module;
    private MyLocation location;

    private IMainServiceManager(Context context){
        this.context = context;
    }

    public static synchronized IMainServiceManager getInstance(Context context){
        if(iMainManager == null){
            synchronized (IMainServiceManager.class){
                if (iMainManager == null){
                    iMainManager = new IMainServiceManager(context);
                }
            }
        }
        return iMainManager;
    }

    public void bindService(){
        LogUtil.i(TAG, "---bindService---");
        if(iMainService == null){
            Intent intent = createExplicitFromImplicitIntent(context, new Intent(HOST_SERVICE_NAME));
            if (intent != null) {
                intent.putExtra(BINDING_NAME, IMainService.class.getName());
                context.bindService(intent, mainConnection, context.BIND_AUTO_CREATE);
            }
        }
    }

    public void unbindService() {
        LogUtil.i(TAG, "---unbindService---");
        if (iMainService != null) {
            context.unbindService(mainConnection);
            iMainService = null;
        }
    }

    public IMainService getService(){
        return this.iMainService;
    }

    public MyLocation getLocation(){
        return this.location;
    }

    public MyModule getModule(){
        return this.module;
    }

    private ServiceConnection mainConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.i(TAG, "---mainConnection onServiceConnected---");
            iMainService = IMainService.Stub.asInterface(service);
            try {
                iMainService.registerCallback(mCallback);
                iMainService.addPid(Process.myPid());
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "---mainConnection onServiceDisconnected---");
            try {
                iMainService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
            iMainService = null;
        }
    };

    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        public void locationChanged(MyLocation myLocation) {
            LogUtil.i(TAG, "locationChanged");
            location = myLocation;
        }

        public void moduleChanged(MyModule myModule) {
            LogUtil.i(TAG, "moduleChanged");
            module = myModule;
        }

        public void exitSystem() {
            LogUtil.i(TAG, "exitSystem");
            unbindService();
            System.exit(0);
        }

    };

    private static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}

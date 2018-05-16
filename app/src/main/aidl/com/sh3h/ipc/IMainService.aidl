// IHostAidlInterface.aidl
package com.sh3h.ipc;

import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.ipc.IRemoteServiceCallback;
// Declare any non-default types here with import statements

interface IMainService {
    void setLocation(in MyLocation myLocation);

    MyLocation getLocation();

    void setMyModule(in MyModule myModule);

    void exitSystem();

    int[] getPid();

    void addPid(int pid);

    void setTimeError(long error);

    long getCurrentTime();

    void registerCallback(IRemoteServiceCallback cb);
    void unregisterCallback(IRemoteServiceCallback cb);
}
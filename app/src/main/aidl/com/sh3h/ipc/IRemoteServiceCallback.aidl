// IRemoteServiceCallback.aidl
package com.sh3h.ipc;

import com.sh3h.ipc.module.MyModule;
import com.sh3h.ipc.location.MyLocation;

// Declare any non-default types here with import statements

/**
 * Example of a callback interface used by IRemoteService to send
 * synchronous notifications back to its clients.  Note that this is a
 * one-way interface so the server does not block waiting for the client.
 */
oneway interface IRemoteServiceCallback {
    /**
     * Called when the service has a new value for you.
     */
    void locationChanged(in MyLocation myLocation);

    void moduleChanged(in MyModule myModule);

    void exitSystem();
}


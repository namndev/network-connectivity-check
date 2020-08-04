package com.namndev.network.monitoring;


import androidx.annotation.NonNull;

/**
 * When implemented by an Activity, it add hooks to network events thanks to ActivityLifecycleCallbacks
 * @see android.app.Application.ActivityLifecycleCallbacks
 */

public interface NetworkConnectivityListener {

    /**
     * Put this at false to disable hooks ( enabled by default )
     */
    default boolean getShouldBeCalled(){ return true; }

    /**
     * Put this at false to disable hooks on resume ( enabled by default )
     */
    default boolean getCheckOnResume() { return true; }

    default boolean isConnected() {return  ConnectivityStateHolder.getInstance().isConnected(); }

    void networkConnectivityChanged(@NonNull ConnectivityEvent event);

}
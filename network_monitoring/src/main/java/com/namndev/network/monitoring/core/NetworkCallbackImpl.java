package com.namndev.network.monitoring.core;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Implementation of ConnectivityManager.NetworkCallback,
 * it stores every change of connectivity into NetworkState
 */
public final class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private final NetworkStateImpl stateHolder;

    private static final String TAG = "NetworkCallbackImpl";


    public NetworkCallbackImpl(@NonNull NetworkStateImpl stateHolder) {
        this.stateHolder = stateHolder;
    }

    private void updateConnectivity(boolean isAvailable, Network network) {
        this.stateHolder.setNetwork(network);
        this.stateHolder.setAvailable(isAvailable);
    }

    //in case of a new network ( wifi enabled ) this is called first
    @Override
    public void onAvailable(@NonNull Network network) {
        Log.i(TAG, "" + '[' + network + "] - new network");
        this.updateConnectivity(true, network);
    }

    //this is called several times in a row, as capabilities are added step by step
    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        Log.i(TAG, "" + '[' + network + "] - network capability changed: " + networkCapabilities);
        this.stateHolder.setNetworkCapabilities(networkCapabilities);
    }

    //this is called after
    @Override
    public void onLost(@NonNull Network network) {
        Log.i(TAG, "" + '[' + network + "] - network lost");
        this.updateConnectivity(false, network);
    }

    @Override
    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
        this.stateHolder.setLinkProperties(linkProperties);
        Log.i(TAG, "" + '[' + network + "] - link changed: " + linkProperties.getInterfaceName());
    }

    @Override
    public void onUnavailable() {
        Log.i(TAG, "Unavailable");
    }

    @Override
    public void onLosing(@NonNull Network network, int maxMsToLive) {
        Log.i(TAG, "" + '[' + network + "] - Losing with " + maxMsToLive);
    }

    @Override
    public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
        Log.i(TAG, "" + '[' + network + "] - Blocked status changed: " + blocked);
    }


}

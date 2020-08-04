package com.namndev.network.monitoring.core;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

public final class NetworkCallbackImp extends ConnectivityManager.NetworkCallback {
    private final NetworkStateImp stateHolder;
    private static final String TAG = "NetworkCallbackImp";

    private void updateConnectivity(boolean isAvailable, Network network) {
        this.stateHolder.setNetwork(network);
        this.stateHolder.setAvailable(isAvailable);
    }

    public void onAvailable(@NonNull Network network) {
        Log.i(TAG, "" + '[' + network + "] - new network");
        this.updateConnectivity(true, network);
    }

    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        Log.i(TAG, "" + '[' + network + "] - network capability changed: " + networkCapabilities);
        this.stateHolder.setNetworkCapabilities(networkCapabilities);
    }

    public void onLost(@NonNull Network network) {
        Log.i(TAG, "" + '[' + network + "] - network lost");
        this.updateConnectivity(false, network);
    }

    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
        this.stateHolder.setLinkProperties(linkProperties);
        Log.i(TAG, "" + '[' + network + "] - link changed: " + linkProperties.getInterfaceName());
    }

    public void onUnavailable() {
        Log.i(TAG, "Unavailable");
    }

    public void onLosing(@NonNull Network network, int maxMsToLive) {
        Log.i(TAG, "" + '[' + network + "] - Losing with " + maxMsToLive);
    }

    public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
        Log.i(TAG, "" + '[' + network + "] - Blocked status changed: " + blocked);
    }

    public NetworkCallbackImp(@NonNull NetworkStateImp stateHolder) {
        this.stateHolder = stateHolder;
    }

}

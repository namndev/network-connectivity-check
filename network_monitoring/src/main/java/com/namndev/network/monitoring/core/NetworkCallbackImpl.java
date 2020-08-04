package com.namndev.network.monitoring.core;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

public final class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private final NetworkStateImpl stateHolder;

    private static final String TAG = "NetworkCallbackImp";


    public NetworkCallbackImpl(@NonNull NetworkStateImpl stateHolder) {
        this.stateHolder = stateHolder;
    }

    private void updateConnectivity(boolean isAvailable, Network network) {
        this.stateHolder.setNetwork(network);
        this.stateHolder.setAvailable(isAvailable);
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        Log.i(TAG, "" + '[' + network + "] - new network");
        this.updateConnectivity(true, network);
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        Log.i(TAG, "" + '[' + network + "] - network capability changed: " + networkCapabilities);
        this.stateHolder.setNetworkCapabilities(networkCapabilities);
    }

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

package com.namndev.network.monitoring.core;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namndev.network.monitoring.ConnectivityStateHolder;
import com.namndev.network.monitoring.NetworkState;
import com.namndev.network.monitoring.events.AvailabilityEvent;
import com.namndev.network.monitoring.events.LinkPropertyChangeEvent;
import com.namndev.network.monitoring.events.NetworkCapabilityEvent;
import com.namndev.network.monitoring.events.NetworkEvent;

public final class NetworkStateImpl implements NetworkState {

    private final NetworkStateCallback callback;

    private boolean isAvailable;
    @Nullable
    private Network network;
    @Nullable
    private LinkProperties linkProperties;
    @Nullable
    private NetworkCapabilities networkCapabilities;

    public NetworkStateImpl(@NonNull NetworkStateCallback callback) {
        this.callback = callback;
    }

    private void notify(NetworkEvent event) {
        callback.callback(this, event);
    }

    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean value) {
        boolean old = this.isAvailable;
        boolean odlIConnected = ConnectivityStateHolder.getInstance().isConnected();
        this.isAvailable = value;
        notify(new AvailabilityEvent(this, old, odlIConnected));
    }

    @Nullable
    public Network getNetwork() {
        return this.network;
    }

    public void setNetwork(@Nullable Network network) {
        this.network = network;
    }

    @Nullable
    public LinkProperties getLinkProperties() {
        return this.linkProperties;
    }

    public void setLinkProperties(@Nullable LinkProperties value) {
        LinkProperties old = this.linkProperties;
        this.linkProperties = value;
        notify(new LinkPropertyChangeEvent(this, old));
    }

    @Nullable
    public NetworkCapabilities getNetworkCapabilities() {
        return this.networkCapabilities;
    }

    public void setNetworkCapabilities(@Nullable NetworkCapabilities value) {
        NetworkCapabilities old = this.networkCapabilities;
        this.networkCapabilities = value;
        notify(new NetworkCapabilityEvent(this, old));
    }
}
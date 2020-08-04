package com.namndev.network.monitoring.core;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namndev.network.monitoring.ConnectivityStateHolder;
import com.namndev.network.monitoring.NetworkState;

public final class NetworkStateImp implements NetworkState {

    private final NetworkEvent.NetworkEventNotify notify;

    private boolean isAvailable;
    @Nullable
    private Network network;
    @Nullable
    private LinkProperties linkProperties;
    @Nullable
    private NetworkCapabilities networkCapabilities;

    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean value) {
        boolean old = this.isAvailable;
        ConnectivityStateHolder var10000 = ConnectivityStateHolder.getInstance();
        boolean odlIConnected = var10000.isConnected();
        this.isAvailable = value;
        this.notify.notify(new NetworkEvent.AvailabilityEvent((NetworkState)this, old, odlIConnected));
    }

    @Nullable
    public Network getNetwork() {
        return this.network;
    }

    public void setNetwork(@Nullable Network var1) {
        this.network = var1;
    }

    @Nullable
    public LinkProperties getLinkProperties() {
        return this.linkProperties;
    }

    public void setLinkProperties(@Nullable LinkProperties value) {
        LinkProperties old = this.linkProperties;
        this.linkProperties = value;
        this.notify.notify(new NetworkEvent.LinkPropertyChangeEvent((NetworkState)this, old));
    }

    @Nullable
    public NetworkCapabilities getNetworkCapabilities() {
        return this.networkCapabilities;
    }

    public void setNetworkCapabilities(@Nullable NetworkCapabilities value) {
        NetworkCapabilities old = this.networkCapabilities;
        this.networkCapabilities = value;
        this.notify.notify(new NetworkEvent.NetworkCapabilityEvent((NetworkState)this, old));
    }

    public NetworkStateImp(@NonNull NetworkStateCallback callback) {
        this.notify = event -> callback.callback(NetworkStateImp.this, event);
    }

}
package com.namndev.network.monitoring.events;

import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namndev.network.monitoring.NetworkState;

public final class NetworkCapabilityEvent implements NetworkEvent {

    @NonNull
    private final NetworkState state;
    @Nullable
    private final NetworkCapabilities old;


    public NetworkCapabilityEvent(@NonNull NetworkState state, @Nullable NetworkCapabilities old) {
        this.state = state;
        this.old = old;
    }

    @NonNull
    @Override
    public NetworkState getState() {
        return this.state;
    }

    @Nullable
    public final NetworkCapabilities getOld() {
        return this.old;
    }

}

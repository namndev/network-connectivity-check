package com.namndev.network.monitoring.events;

import androidx.annotation.NonNull;

import com.namndev.network.monitoring.NetworkState;

public final class AvailabilityEvent implements NetworkEvent {

    @NonNull
    private final NetworkState state;
    private final boolean oldNetworkAvailability;
    private final boolean oldConnectivity;

    @NonNull
    @Override
    public NetworkState getState() {
        return this.state;
    }

    public boolean getOldNetworkAvailability() {
        return this.oldNetworkAvailability;
    }

    public final boolean getOldConnectivity() {
        return this.oldConnectivity;
    }

    public AvailabilityEvent(@NonNull NetworkState state, boolean oldNetworkAvailability, boolean oldConnectivity) {
        this.state = state;
        this.oldNetworkAvailability = oldNetworkAvailability;
        this.oldConnectivity = oldConnectivity;
    }
}

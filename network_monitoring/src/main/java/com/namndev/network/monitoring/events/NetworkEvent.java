package com.namndev.network.monitoring.events;

import androidx.annotation.NonNull;

import com.namndev.network.monitoring.NetworkState;

public interface NetworkEvent {
    @NonNull
    NetworkState getState();
}

package com.namndev.network.monitoring;


public interface ConnectivityState {

    /**
     * Stored connectivity state of the device
     * True if the device has a available network
     */
    default boolean isConnected() {
        return !NUtils.filter(networkStats(), NetworkState::isAvailable).isEmpty();
    }

    /**
     * The stats of the networks being used by the device
     */
    Iterable<NetworkState> networkStats();
}

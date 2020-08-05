package com.namndev.network.monitoring;

/**
 * Enables synchronous and asynchronous connectivity state checking thanks to LiveData and stored states.
 *
 * @see ConnectivityState#isConnected to get the instance connectivity state
 * @see NetworkLiveData to observe connectivity changes
 */
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

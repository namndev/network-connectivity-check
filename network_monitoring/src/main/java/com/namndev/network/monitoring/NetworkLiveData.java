package com.namndev.network.monitoring;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This liveData enabling network connectivity monitoring
 *
 * @see ConnectivityStateHolder to get current connection state
 */
public final class NetworkLiveData extends LiveData<ConnectivityEvent> {

    private NetworkLiveData() {
    }

    public static NetworkLiveData getInstance() {
        return NetworkLiveDataHelper.INSTANCE;
    }

    private static class NetworkLiveDataHelper {
        private static final NetworkLiveData INSTANCE = new NetworkLiveData();
    }

    public void notify(@NonNull ConnectivityEvent event) {
        this.postValue(event);
    }
}
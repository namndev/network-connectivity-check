package com.namndev.network.monitoring;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This liveData enabling network connectivity monitoring
 * @see ConnectivityStateHolder to get current connection state
 */
public class NetworkEvents extends LiveData<ConnectivityEvent> {

    public void notify(@NonNull ConnectivityEvent event) {
        this.postValue(event);
    }

    private NetworkEvents() {
    }

    public static NetworkEvents getInstance() {
        return NetworkEventsHelper.INSTANCE;
    }

    private static class NetworkEventsHelper {
        private static final NetworkEvents INSTANCE = new NetworkEvents();
    }

}
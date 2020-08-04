package com.namndev.network.monitoring;

public class ConnectivityEvent {

    private final boolean connected;

    public ConnectivityEvent(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return this.connected;
    }
}

package com.namndev.network.monitoring;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import com.namndev.network.monitoring.events.NetworkEvent;

public interface NetworkState {

    boolean isAvailable();

    /**
     * The network being used by the device
     */
    Network getNetwork();

    /**
     * Network Capabilities
     */
    NetworkCapabilities getNetworkCapabilities();

    /**
     * Link Properties
     */
    LinkProperties getLinkProperties();

    /**
     * Check if the network is Wifi ( shortcut )
     */
    default boolean isWifi() {
        return getNetworkCapabilities() != null && getNetworkCapabilities().hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }

    /**
     * Check if the network is Mobile ( shortcut )
     */
    default boolean isMobile() {
        return getNetworkCapabilities() != null && getNetworkCapabilities().hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
    }

    /**
     * Get the interface name ( shortcut )
     */
    default String getInterfaceName() {
        return getLinkProperties() != null ? getLinkProperties().getInterfaceName() : null;
    }

    interface NetworkStateCallback {
        void callback(NetworkState state, NetworkEvent event);
    }
}

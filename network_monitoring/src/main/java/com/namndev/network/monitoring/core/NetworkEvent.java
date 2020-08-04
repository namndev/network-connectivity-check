package com.namndev.network.monitoring.core;

import android.net.LinkProperties;
import android.net.NetworkCapabilities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namndev.network.monitoring.NetworkState;

public abstract class NetworkEvent {

    @NonNull
    public abstract NetworkState getState();

    private NetworkEvent() {
    }

    public interface NetworkEventNotify {
        void notify(NetworkEvent event);
    }

    public static final class AvailabilityEvent extends NetworkEvent {
        @NonNull
        private final NetworkState state;
        private final boolean oldNetworkAvailability;
        private final boolean oldConnectivity;

        @NonNull
        public NetworkState getState() {
            return this.state;
        }

        public final boolean getOldNetworkAvailability() {
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

    public static final class NetworkCapabilityEvent extends NetworkEvent {
        @NonNull
        private final NetworkState state;
        @Nullable
        private final NetworkCapabilities old;

        @NonNull
        public NetworkState getState() {
            return this.state;
        }

        @Nullable
        public final NetworkCapabilities getOld() {
            return this.old;
        }

        public NetworkCapabilityEvent(@NonNull NetworkState state, @Nullable NetworkCapabilities old) {
            this.state = state;
            this.old = old;
        }
    }

    public static final class LinkPropertyChangeEvent extends NetworkEvent {
        @NonNull
        private final NetworkState state;
        @Nullable
        private final LinkProperties old;

        @NonNull
        public NetworkState getState() {
            return this.state;
        }

        @Nullable
        public final LinkProperties getOld() {
            return this.old;
        }

        public LinkPropertyChangeEvent(@NonNull NetworkState state, @Nullable LinkProperties old) {
            this.state = state;
            this.old = old;
        }
    }
}

package com.namndev.network.monitoring.events;

import android.net.LinkProperties;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namndev.network.monitoring.NetworkState;

public final class LinkPropertyChangeEvent implements NetworkEvent {

    public LinkPropertyChangeEvent(@NonNull NetworkState state, @Nullable LinkProperties old) {
        this.state = state;
        this.old = old;
    }

    @NonNull
    private final NetworkState state;
    @Nullable
    private final LinkProperties old;

    @NonNull
    @Override
    public NetworkState getState() {
        return this.state;
    }

    @Nullable
    public final LinkProperties getOld() {
        return this.old;
    }

}

package com.namndev.network.monitoring;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import com.namndev.network.monitoring.core.ActivityLifecycleCallbacksImp;
import com.namndev.network.monitoring.core.NetworkCallbackImp;
import com.namndev.network.monitoring.core.NetworkEvent;
import com.namndev.network.monitoring.core.NetworkStateImp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.namndev.network.monitoring.core.NUtils;

public class ConnectivityStateHolder implements ConnectivityState {

    private Set<NetworkState> networkStates = new HashSet<>();

    @Override
    public Iterable<NetworkState> networkStats() {
        return networkStates;
    }


    private void networkEventHandler(NetworkState state, NetworkEvent event) {
        if(event instanceof NetworkEvent.AvailabilityEvent){
            if (isConnected() != ((NetworkEvent.AvailabilityEvent) event).getOldNetworkAvailability()) {
                NetworkEvents.getInstance().notify(new ConnectivityEvent(state.isAvailable()));
            }
        }
    }

    /**
     * This starts the broadcast of network events to NetworkState and all Activity implementing NetworkConnectivityListener
     * @see NetworkState
     * @see NetworkConnectivityListener
     */


    public void registerConnectivityBroadcaster(Application application) {
        //register the Activity Broadcaster
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImp());

        //get connectivity manager
        ConnectivityManager connectivityManager =
                (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);

        NUtils.forEach(Arrays.asList(
                new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build(),
                new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        ), req -> {
            NetworkStateImp stateHolder = new NetworkStateImp(this::networkEventHandler);
            networkStates.add(stateHolder);
            connectivityManager.registerNetworkCallback(req, new NetworkCallbackImp(stateHolder));
        });
    }

    public static ConnectivityStateHolder getInstance() {
        return ConnectivityStateHolderHelper.INSTANCE;
    }


    private static class ConnectivityStateHolderHelper {
        private static final ConnectivityStateHolder INSTANCE = new ConnectivityStateHolder();
    }
}

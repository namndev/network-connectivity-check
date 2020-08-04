package com.namndev.network.monitoring.core;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.namndev.network.monitoring.ConnectivityEvent;
import com.namndev.network.monitoring.ConnectivityStateHolder;
import com.namndev.network.monitoring.NetworkConnectivityListener;
import com.namndev.network.monitoring.NetworkEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NUtils {

    private static final String ID_KEY = "network.monitoring.previousState";

    /**
     * Returns a stream consisting of the elements of this stream that match
     * the given predicate.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to each element to determine if it
     *                  should be included
     * @return the new List
     */
    public static <T> List<T> filter(@NonNull Iterable<T> list, NPre<T, Boolean> predicate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
           return StreamSupport.stream(list.spliterator(), false)
                   .filter(predicate::get).collect(Collectors.toList());
        else {
            List<T> col = new ArrayList<>();
            for (T t: list)
                if (predicate.get(t))
                    col.add(t);
            return col;
        }
    }

    public static <T> void forEach(Iterable<T> list, NConsumer<? super T> action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            list.forEach(action::accept);
        else {
            for (T t: list) {
                action.accept(t);
            }
        }
    }

    public interface NPre<T, R> {
        R get(T item);
    }

    public interface NConsumer<T> {
        void accept(T var1);
    }

    public interface NFunctional {
        void accept();
    }

    public static void onListenerCreated(NetworkConnectivityListener ncl) {

        NetworkEvents.getInstance().observe((LifecycleOwner) ncl, connectivityEvent -> {
            if (getPreviousState(ncl) != null)
                ncl.networkConnectivityChanged(connectivityEvent);
        });

    }


    public static void onListenerResume(NetworkConnectivityListener ncl) {
        if (!ncl.getShouldBeCalled() || !ncl.getCheckOnResume()) return;
        Boolean ps = getPreviousState(ncl);
        boolean previousState = ps != null && ps;
        boolean isConnected = ConnectivityStateHolder.getInstance().isConnected();
        setPreviousState(ncl, isConnected);
        boolean connectionLost = previousState && !isConnected;
        boolean connectionBack = !previousState && isConnected;
        if (connectionLost || connectionBack) {
            ncl.networkConnectivityChanged(new ConnectivityEvent(isConnected));
        }
    }

    public static void setPreviousState(NetworkConnectivityListener ncl, boolean value) {
        if(ncl instanceof Fragment){
            Fragment f = (Fragment) ncl;
            Bundle a = f.getArguments();
            if( a == null) {
                a = new Bundle();
            }
            a.putInt(ID_KEY, value ? 1 : 0);
            f.setArguments(a);
        }
        if(ncl instanceof Activity){
            Activity act = (Activity) ncl;
            Bundle a = act.getIntent().getExtras();
            if( a ==null) {
                a = new Bundle();
            }
            a.putInt(ID_KEY, value ? 1 : 0);
            act.getIntent().replaceExtras(a);
        }
    }

    public static Boolean getPreviousState(NetworkConnectivityListener ncl) {
        if(ncl instanceof Fragment){
            Fragment f = (Fragment) ncl;
            Bundle a = f.getArguments();
            if(a == null) return null;
            int value = a.getInt(ID_KEY, -1);
            if(value == -1) return null;
            return value == 1;
        }
        if(ncl instanceof Activity){
            Activity act = (Activity) ncl;
            Bundle a = act.getIntent().getExtras();
            if(a == null) return null;
            int value = a.getInt(ID_KEY, -1);
            if(value == -1) return null;
            return value == 1;
        }
        return null;
    }


    public static void safeRun(String tag, NFunctional functional) {
        try {
            //register to network events
            functional.accept();
        } catch (Throwable e) {
            //ignore but log it
            Log.e(tag, e.toString());
        }
    }
}

package com.namndev.network.monitoring.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.namndev.network.monitoring.NUtils;
import com.namndev.network.monitoring.NetworkConnectivityListener;

/**
 * This is the implementation Application.ActivityLifecycleCallbacksImp,
 * it calls the methods of NetworkConnectivityListener in the activity implementing it,
 * thus enabling
 *
 * @see Application.ActivityLifecycleCallbacks
 */
public final class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle p1) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        if (!(activity instanceof LifecycleOwner)) return;
        if (activity instanceof FragmentActivity)
            addLifecycleCallbackToFragments((FragmentActivity) activity);

        if (!(activity instanceof NetworkConnectivityListener) || !((NetworkConnectivityListener) activity).getShouldBeCalled())
            return;
        NUtils.onListenerCreated((NetworkConnectivityListener) activity);
    }

    public void onActivityResumed(@NonNull Activity activity) {
        if (!(activity instanceof LifecycleOwner)) return;
        if (!(activity instanceof NetworkConnectivityListener)) return;
        NUtils.onListenerResume((NetworkConnectivityListener) activity);
    }

    private void addLifecycleCallbackToFragments(FragmentActivity activity) {
        NUtils.safeRun("ActivityCallbacks", () -> {
            FragmentManager.FragmentLifecycleCallbacks callbacks = new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedState) {
                    if (!(f instanceof NetworkConnectivityListener) || !((NetworkConnectivityListener) f).getShouldBeCalled())
                        return;
                    NUtils.onListenerCreated((NetworkConnectivityListener) f);
                }

                @Override
                public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    if (f instanceof NetworkConnectivityListener)
                        NUtils.onListenerResume((NetworkConnectivityListener) f);
                }
            };
            activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(callbacks, true);
        });
    }
}
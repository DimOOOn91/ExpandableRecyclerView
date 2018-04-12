package com.lisovyi.dima.expendablerecyclerview.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public class AndroidUtil {

    /**
     * Check current network connection state
     * @param context - any non null Context
     * @return {@code true} if any ("WIFI" or "MOBILE") network connection exists
     */
    public static boolean internetConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ?
                connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

}

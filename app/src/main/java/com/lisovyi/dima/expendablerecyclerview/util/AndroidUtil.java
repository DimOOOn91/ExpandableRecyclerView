package com.lisovyi.dima.expendablerecyclerview.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lisovyi.dima.expendablerecyclerview.R;

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



    public static AlertDialog createDialog(Context context, String message,
                                            final View.OnClickListener positiveButtonListener) {
        View dialogView = View.inflate(context, R.layout.dialog_styled, null);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        TextView tvMessage = (TextView) dialogView.findViewById(R.id.tv_message);
        tvMessage.setText(message);

        Button positiveButton = (Button) dialogView.findViewById(R.id.positive_button);
        Button negativeButton = (Button) dialogView.findViewById(R.id.negative_button);

        if (positiveButtonListener != null) {
            positiveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonListener.onClick(v);
                    dialog.dismiss();
                }
            });
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            positiveButton.setVisibility(View.GONE);
            negativeButton.setText(android.R.string.ok);
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        negativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}

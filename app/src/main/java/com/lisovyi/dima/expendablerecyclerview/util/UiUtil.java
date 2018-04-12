package com.lisovyi.dima.expendablerecyclerview.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class UiUtil {


    public static AlertDialog createDialog(Context context, String message,
                                           final DialogInterface.OnClickListener positiveButtonListener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        positiveButtonListener.onClick(dialog1, which);
                        dialog1.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        dialog1.dismiss();
                    }
                })
                .create();
    }

}

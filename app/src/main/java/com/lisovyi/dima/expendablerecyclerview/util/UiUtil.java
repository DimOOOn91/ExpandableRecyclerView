package com.lisovyi.dima.expendablerecyclerview.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class UiUtil {

    public static void showMessage(Context context, @StringRes int stringRes) {
        Toast.makeText(context, stringRes, Toast.LENGTH_LONG).show();
    }

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

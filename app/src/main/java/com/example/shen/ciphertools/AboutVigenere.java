package com.example.shen.ciphertools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

/**
 * Created by Shen Chang-Shao on 2016/3/10.
 */
public class AboutVigenere extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.about_vigenere)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(18);
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.setMargins(0, 0, 0, 10);
                btnPositive.setLayoutParams(params);
            }
        });
        return alert;
    }
}
/*// Use the Builder class for convenient dialog construction
AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
builder.setMessage(R.string.dialog_fire_missiles)
        .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        // FIRE ZE MISSILES!
        }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
        }
        });
        // Create the AlertDialog object and return it
        return builder.create();
        */
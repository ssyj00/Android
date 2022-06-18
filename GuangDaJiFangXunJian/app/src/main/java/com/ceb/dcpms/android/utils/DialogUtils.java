package com.ceb.dcpms.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ceb.dcpms.android.R;

public class DialogUtils {

    public static void showDialog(Activity activity, String message, String button, View.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setText(button);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(onClickListener != null){
                    onClickListener.onClick(v);
                }
            }
        });

        dialog.show();
    }

    public static void showDialog(Activity activity, int message, int button, View.OnClickListener onClickListener){
        showDialog(activity, activity.getResources().getString(message), activity.getResources().getString(button), onClickListener);
    }
}

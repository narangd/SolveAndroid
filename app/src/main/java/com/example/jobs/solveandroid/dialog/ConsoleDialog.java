package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jobs.solveandroid.R;

/**
 * Created by jobs on 2017. 5. 12..
 */

public class ConsoleDialog {

    private AlertDialog.Builder builder;
    private TextView sourceView;

    public ConsoleDialog(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_console, null, false);
        sourceView = (TextView) root.findViewById(R.id.console_view);
        builder = new AlertDialog.Builder(context)
                .setTitle("Console")
                .setView(root);
    }

    public ConsoleDialog print(String data) {
        sourceView.setText(data);
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }
}

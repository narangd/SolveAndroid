package com.example.jobs.solveandroid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jobs.solveandroid.R;

/**
 * Created by jobs on 2017. 5. 12..
 */

public class SourceDialog {

    private AlertDialog.Builder builder;
    private TextView sourceView;

    public SourceDialog(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_source, null, false);
        sourceView = (TextView) root.findViewById(R.id.source_view);
        builder = new AlertDialog.Builder(context)
                .setTitle("Java Source")
                .setView(root);
    }

    public SourceDialog setSource(CharSequence source) {
        sourceView.setText(source);
        return this;
    }

    public SourceDialog setSendButton(boolean enable, Dialog.OnClickListener onClickListener) {
        if (enable) {
            builder.setPositiveButton("Send", onClickListener);
        }
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }

}

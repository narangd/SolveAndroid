package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jobs.solveandroid.R;
import com.example.jobs.solveandroid.editor.Type;

/**
 * Created by jobs on 2017. 4. 12..
 */

public class VariableDialog {
    private AlertDialog.Builder builder;

    public VariableDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("어떤 변수를 생성하시겠어요?")
                .setView(createView(context));
    }

    private View createView(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_create_variable, null);
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                context,
                android.R.layout.simple_list_item_1,
                Type.values()
        ));
        return root;
    }

    public VariableDialog setCreateButton(DialogInterface.OnClickListener onClickListener) {
        builder.setPositiveButton("생성", onClickListener);
        return this;
    }

    public AlertDialog create() {
        return builder.create();
    }

    public void show() {
        create().show();
    }
}

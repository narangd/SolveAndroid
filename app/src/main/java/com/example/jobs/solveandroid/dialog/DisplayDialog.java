package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobs.solveandroid.R;
import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.List;

/**
 * Created by jobs on 2017. 5. 15..
 */

public class DisplayDialog {

    private AlertDialog.Builder builder;
    private ArrayAdapter<String> arrayAdapter;
    private DialogInterface.OnClickListener onClickListener;

    public DisplayDialog(Context context) {
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        builder = new AlertDialog.Builder(context)
                .setTitle("Console")
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onClickListener != null) {
                            onClickListener.onClick(dialog, which-1);
                        }
                    }
                });
    }

    public DisplayDialog setVariableList(List<Variable> variableList) {
        arrayAdapter.add("# NewLine #");
        for (Variable variable : variableList) {
            arrayAdapter.add(variable.name);
        }
        return this;
    }

    public DisplayDialog setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }
}

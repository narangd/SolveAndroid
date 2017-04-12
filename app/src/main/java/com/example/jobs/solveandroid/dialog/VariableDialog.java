package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

    private View createView(final Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_create_variable, null);
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
//        http://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html
        spinner.setAdapter(new ArrayAdapter<Type>(
                context,
                android.R.layout.simple_list_item_1,
                Type.values()
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View root = super.getView(position, convertView, parent);
                TextView textView = (TextView) root.findViewById(android.R.id.text1);
//                textView.setTextColor(0xffffff);
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                return root;
            }
        });
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

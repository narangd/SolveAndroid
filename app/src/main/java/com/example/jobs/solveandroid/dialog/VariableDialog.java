package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jobs.solveandroid.R;
import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;
import com.example.jobs.solveandroid.util.StringUtil;

/**
 * Created by jobs on 2017. 4. 12..
 */

public class VariableDialog {
    private static int typeIndex = 0;

    private AlertDialog.Builder builder;
    private OnPositive onPositive;
    private Type type;
    private EditText nameEditText;
    private EditText valueEditText;
    private Spinner spinner;

    public VariableDialog(Context context) {
        builder = new AlertDialog.Builder(context, R.style.VariableDialog);
        builder.setTitle("What kind of variable?")
                .setView(createView(context));
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public VariableDialog(Context context, Variable variable) {
        this(context);
        nameEditText.setText(variable.name);
        Type[] types = Type.values();
        for (int i=0; i<types.length; i++) {
            if (types[i] == variable.type) {
                spinner.setSelection(i);
                break;
            }
        }
        valueEditText.setText(variable.valueString());
    }

    private View createView(final Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_create_variable, null);
        spinner = (Spinner) root.findViewById(R.id.spinner);
        nameEditText = (EditText) root.findViewById(R.id.name);
        valueEditText = (EditText) root.findViewById(R.id.value);

//        http://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.type,
                R.layout.spinner_variable_item
//                android.R.layout.simple_list_item_1
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_variable_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(typeIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = Type.values()[position];
                typeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        nameEditText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL)
//                    return false;
//                else if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
//                    return false;
//                }
////                char ch = (char)event.getUnicodeChar();
////                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
////                    return false;
////                }
//                return true;
//            }
//        });

        return root;
    }

    public VariableDialog setCreateButton(String caption, final OnPositive onPositive) {
        this.onPositive = onPositive;

        builder.setPositiveButton(caption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return this;
    }

    private boolean validate() {
        return onPositive != null &&
                !StringUtil.isEmpty(nameEditText.getText().toString()) &&
                !StringUtil.isEmpty(valueEditText.getText().toString());
    }


    public AlertDialog create() {
        AlertDialog alertDialog = builder.create();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        String name = nameEditText.getText().toString();
                        String value = valueEditText.getText().toString();
                        Variable variable = Variable.fromType(type, name, value);
                        VariableDialog.this.onPositive.onVariable(variable);
                    }
                }
            });
        }
        return alertDialog;
    }

    public void show() {
        create().show();
    }

    public interface OnPositive {
        void onVariable(Variable variable);
    }
}

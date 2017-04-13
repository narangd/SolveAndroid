package com.example.jobs.solveandroid.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private OnCreate onCreate;
    private Type type;
    private EditText nameEditText;
    private EditText valueEditText;

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

    private View createView(final Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_create_variable, null);
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
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

        nameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                char ch = (char)event.getUnicodeChar();
                if (ch >= 'a' && ch <= 'z' && ch >= 'A' && ch <= 'Z') {
                    return false;
                }
                return true;
            }
        });

        return root;
    }

    public VariableDialog setCreateButton(final OnCreate onCreate) {
        this.onCreate = onCreate;
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validate()) {
                    String name = nameEditText.getText().toString();
                    String value = valueEditText.getText().toString();
                    Variable variable = Variable.fromType(type, name, value);
                    VariableDialog.this.onCreate.createVariable(variable);
                }
            }
        });
        return this;
    }

    private boolean validate() {
        return onCreate != null &&
                StringUtil.isEmpty(nameEditText.getText().toString()) &&
                StringUtil.isEmpty(valueEditText.getText().toString());
    }


    public AlertDialog create() {
        return builder.create();
    }

    public void show() {
        create().show();
    }

    public interface OnCreate {
        void createVariable(Variable variable);
    }
}

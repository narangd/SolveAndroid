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
        builder = new AlertDialog.Builder(context);
        builder.setTitle("What kind of variable?")
                .setView(createView(context));
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

        valueEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                char ch = (char)event.getUnicodeChar();
//                if (ch > '')
                return false;
            }
        });

        return root;
    }

    public VariableDialog setCreateButton(final OnCreate onCreate) {
        this.onCreate = onCreate;
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (VariableDialog.this.onCreate != null) {
                    String name = nameEditText.getText().toString();
                    String value = valueEditText.getText().toString();
                    Variable variable;
                    switch (type) {
                        case Byte:
                            variable = new Variable(name, Byte.parseByte(value));
                            break;
                        case Short:
                            variable = new Variable(name, Short.parseShort(value));
                            break;
                        case Integer:
                            variable = new Variable(name, Integer.parseInt(value));
                            break;
                        case Long:
                            variable = new Variable(name, Long.parseLong(value));
                            break;
                        case Float:
                            variable = new Variable(name, Float.parseFloat(value));
                            break;
                        case Double:
                            variable = new Variable(name, Double.parseDouble(value));
                            break;
                        case Character:
                            variable = new Variable(name, value.charAt(0));
                            break;
                        case String:
                        default:
                            variable = new Variable(name, value);
                    }
                    VariableDialog.this.onCreate.createVariable(variable);
                }
            }
        });
        return this;
    }



    public AlertDialog create() {
        return builder.create();
    }

    public void show() {
        create().show();
    }

    public void setOnCreate(OnCreate onCreate) {
    }

    public static interface OnCreate {
        void createVariable(Variable variable);
    }
}

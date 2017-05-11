package com.example.jobs.solveandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * A login screen that offers login via email/password.
 */
public class VariableActivity extends AppCompatActivity {
    public static final String Key_Method = "method";
    public static final String Key_Variable = "variable";
    public static final String Key_Position = "position";
    public static final int ResultCode_Create = 10;
    public static final int ResultCode_Update = 20;
    public static final int ResultCode_Delete = 30;
    private static int typeIndex = 0;

    private Type type;
    private EditText nameEditText;
    private EditText valueEditText;
    private Spinner spinner;
    private Button button;

    private Variable variable;
    private boolean showDelete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable);

        spinner = (Spinner) findViewById(R.id.spinner);
        nameEditText = (EditText) findViewById(R.id.name);
        valueEditText = (EditText) findViewById(R.id.value);
        button = (Button) findViewById(R.id.button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        http://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.type,
                R.layout.spinner_variable_item
//                android.R.layout.simple_list_item_1
        );

        // Spinner
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

        // Button Create/Update
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra(Key_Variable, getVariable()); // update
                setResult(Activity.RESULT_OK/*ResultCode_Create*/, intent);
//                finishActivity();
                finish();
            }
        });

        initialize();
    }

    private void initialize() {
        Intent intent = getIntent();
        if (intent != null) {
            int resultCode = intent.getIntExtra(Key_Method, ResultCode_Create);
            variable = (Variable) intent.getSerializableExtra(Key_Variable);
            switch (resultCode) {
                case ResultCode_Create:
                    button.setText(getString(R.string.button_caption_create));
                    showDelete = false;
                    break;
                case ResultCode_Update:
                    int i=0;
                    for (Type type : Type.values()) {
                        if (type == variable.type) {
                            this.type = type;
                            spinner.setSelection(i);
                            break;
                        }
                        i++;
                    }
                    nameEditText.setText(variable.name);
                    valueEditText.setText(variable.value.toString());
                    button.setText(getString(R.string.button_caption_update));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showDelete) {
            getMenuInflater().inflate(R.menu.menu_variable, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = getIntent();
                                intent.putExtra(Key_Variable, getVariable()); // update
                                intent.putExtra(Key_Method, ResultCode_Delete);
                                setResult(Activity.RESULT_OK/*ResultCode_Create*/, intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Variable getVariable() {
        String name = nameEditText.getText().toString();
        String value = valueEditText.getText().toString();

        // validation...

        Variable createVariable = Variable.fromType(type, name, value);
        if (variable != null) {
            variable.copy(createVariable);
        } else {
            variable = createVariable;
        }
        return variable;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}


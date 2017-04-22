package com.example.jobs.solveandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jobs.solveandroid.dialog.VariableDialog;
import com.example.jobs.solveandroid.editor.Type;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class VariableActivity extends AppCompatActivity {
    private static int typeIndex = 0;

    private Type type;
    private EditText nameEditText;
    private EditText valueEditText;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable);

        spinner = (Spinner) findViewById(R.id.spinner);
        nameEditText = (EditText) findViewById(R.id.name);
        valueEditText = (EditText) findViewById(R.id.value);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Variable");
        setSupportActionBar(toolbar);

//        http://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
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


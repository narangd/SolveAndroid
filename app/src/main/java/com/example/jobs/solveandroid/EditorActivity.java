package com.example.jobs.solveandroid;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;

import java.util.StringTokenizer;

public class EditorActivity extends AppCompatActivity {

    private JavaGenerator javaGenerator = new JavaGenerator("Main");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView contentView = (TextView) findViewById(R.id.contentView);
        final TextView originalView = (TextView) findViewById(R.id.originalView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        javaGenerator.variable.add("name", "김성용");
        javaGenerator.command.print(javaGenerator.variable.get("name"));
        Log.i("Log", "onCreate: " + javaGenerator.toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                progressDialog.show();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String content = javaGenerator.toString();
                        final String highlighted = javaHighlighter(content);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    contentView.setText(Html.fromHtml(highlighted, Html.FROM_HTML_MODE_LEGACY));
                                } else {
                                    contentView.setText(Html.fromHtml(highlighted));
                                }
                                originalView.setText(highlighted);
                                progressDialog.hide();
                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String javaHighlighter(String string) {
        PrettifyHighlighter highlighter = new PrettifyHighlighter();
        String prepare = highlighter.highlight("java", string);

        int depth = 0;
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(prepare, "{}", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            System.out.printf("%s \n", token);
            builder.append(token);
            switch (token) {
                case "{":
                    depth++;
                    for (int i=0; i<depth; i++) {
                        builder.append("&nbsp;");
                    }
                    break;
                case "}":
                    depth--;
                    break;
            }
        }
        return builder.toString().replace("\n", "\n<br/>");
    }
}

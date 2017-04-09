package com.example.jobs.solveandroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jobs.solveandroid.editor.JavaAdapter;
import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.editor.component.Variable;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;

import java.util.StringTokenizer;

public class EditorActivity extends AppCompatActivity {

    private JavaGenerator javaGenerator = new JavaGenerator("Main");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        javaGenerator.addLocalVariable("name", "김성용");
        javaGenerator.addLocalVariable("count", 10);
        for (Variable variable : javaGenerator.getVariables()) {
            javaGenerator.command.print(variable);
        }
        Log.i("Log", "onCreate: " + javaGenerator.toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView contentView = (TextView) findViewById(R.id.contentView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // specify an adapter (see also next example)
        JavaAdapter adapter = new JavaAdapter(javaGenerator);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//        builder.append("&nbsp;");
        StringTokenizer tokenizer = new StringTokenizer(prepare, "{}\n ", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            builder.append(token);
            switch (token) {
                case "{":
                    depth++;
                    break;
                case "}":
                    depth--;
                    break;
                case "\n":
                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken();
                        if (token.equals(" ")) {
                            builder.append("&nbsp;");
                        } else {
                            builder.append(token);
                            break;
                        }
                        builder.append(token);
                    }
                    break;
            }
        }
        return builder.toString().replace("\n", "\n<br/>");
    }
}

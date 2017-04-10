package com.example.jobs.solveandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jobs.solveandroid.editor.JavaAdapter;
import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.editor.component.Variable;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EditorActivity extends AppCompatActivity {

    private JavaGenerator javaGenerator = new JavaGenerator("Main");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        javaGenerator.addLocalVariable("name", "김성용diahefhowhoe---wieeuwefwefwefwefwef23fiuha3f87a387fg234");
        javaGenerator.addLocalVariable("count", 10);
        javaGenerator.addLocalVariable("title_of_activity", "제목");
        javaGenerator.addLocalVariable("length", 1);
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
        progressDialog.setMessage("업데이트 중입니다");

        updateJavaCode(contentView);
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FloatingActionButton fabPrint = (FloatingActionButton) findViewById(R.id.fab_print);
        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Variable[] variables = javaGenerator.getVariables();
                new AlertDialog.Builder(v.getContext())
                        .setTitle("변수선택")
                .setAdapter(
                        new ArrayAdapter<>(
                                v.getContext(),
                                android.R.layout.simple_list_item_1,
                                variables
                        ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                javaGenerator.command.print(variables[which]);
                                updateJavaCode(contentView);
                            }
                        }
                )
                .show();
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

    private void updateJavaCode(final TextView contentView) {
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

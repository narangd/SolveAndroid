package com.example.jobs.solveandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jobs.solveandroid.dialog.ConsoleDialog;
import com.example.jobs.solveandroid.dialog.SourceDialog;
import com.example.jobs.solveandroid.editor.Source;
import com.example.jobs.solveandroid.editor.adapter.JavaAdapter;
import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.editor.component.Variable;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Arrays;
import java.util.StringTokenizer;

public class EditorActivity extends AppCompatActivity {
    public static final int RequestCode_Variable = 100;
    public static final String Key_RequestSource = "source";

    private JavaGenerator javaGenerator = new JavaGenerator("Main");
    private ProgressDialog progressDialog;
    private JavaAdapter javaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");

        // Test data...
        javaGenerator.addLocalVariable("name", "sykim");
        javaGenerator.addLocalVariable("count", 10);
        javaGenerator.addLocalVariable("title_of_activity", "title...");
        javaGenerator.addLocalVariable("length", 1);
        for (Variable variable : javaGenerator.variable.list()) {
            javaGenerator.command.print(variable);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main");
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // specify an javaAdapter (see also next example)
        javaAdapter = new JavaAdapter(javaGenerator);
        javaAdapter.setVariableOnClickListener(new JavaAdapter.OnClickListener<Variable>() {
            @Override
            public void onClick(int pos, Variable variable) {
                Intent intent = new Intent(EditorActivity.this, VariableActivity.class);
                intent.putExtra(VariableActivity.Key_Method, VariableActivity.ResultCode_Update);
                intent.putExtra(VariableActivity.Key_Variable, variable);
                intent.putExtra(VariableActivity.Key_Position, pos);
                startActivityForResult(intent, RequestCode_Variable);
            }
        });
        recyclerView.setAdapter(javaAdapter);

        final FloatingActionMenu fabAddMenu = (FloatingActionMenu) findViewById(R.id.fab_add);
        FloatingActionButton fabVariable = (FloatingActionButton) findViewById(R.id.fab_variable);
        fabVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAddMenu.close(false);

                Intent intent = new Intent(EditorActivity.this, VariableActivity.class);
                intent.putExtra(VariableActivity.Key_Method, VariableActivity.ResultCode_Create);
                intent.putExtra(VariableActivity.Key_Position, javaGenerator.variable.size());
                startActivityForResult(intent, RequestCode_Variable);
            }
        });
        final FloatingActionButton fabPrint = (FloatingActionButton) findViewById(R.id.fab_print);
        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAddMenu.close(true);
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Select Variable")
                .setAdapter(
                        new ArrayAdapter<Variable>(
                                v.getContext(),
                                android.R.layout.simple_list_item_1,
                                javaGenerator.variable.list()
                        ) {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View root = super.getView(position, convertView, parent);
                                TextView textView = (TextView) root.findViewById(android.R.id.text1);
                                Variable variable = this.getItem(position);
                                if (textView != null && variable != null) {
                                    textView.setText(variable.name);
                                }
                                return root;
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                javaGenerator.command.print(javaGenerator.variable.get(which));
                                javaAdapter.notifyItemInserted(javaGenerator.size()-1);
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
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_print:
                updateJavaCode(new SetTextable() {
                    @Override
                    public void setText(final Source source) {
                        boolean enable = false;
                        if (getIntent() != null) {
                            enable = getIntent().getStringExtra(Key_RequestSource) != null;
                        }
                        new SourceDialog(EditorActivity.this)
                                .setSource(source.html)
                                .setSendButton(enable, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.putExtra(Key_RequestSource, source.code);
                                        setResult(Activity.RESULT_OK, intent);
                                        finish();
                                    }
                                })
                                .show();
                    }
                });
                return true;
            case R.id.action_play:
                new ConsoleDialog(EditorActivity.this)
                        .print(javaGenerator.toConsole())
                        .show();
                return true;
            case R.id.action_refresh:
//                javaAdapter.notifyDataSetChanged();
                new AlertDialog.Builder(this)
                        .setMessage(Arrays.toString(javaGenerator.variable.list().toArray()))
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode : " + requestCode);
        System.out.println("resultCode : " + resultCode);
        System.out.println("data : " + data);
        if (data != null) {
            System.out.println("data.getExtras() : " + data.getExtras());
        }
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case RequestCode_Variable:
                Variable variable = (Variable) data.getSerializableExtra(VariableActivity.Key_Variable);
                resultCode = data.getIntExtra(VariableActivity.Key_Method, VariableActivity.ResultCode_Create);
                int position = data.getIntExtra(VariableActivity.Key_Position, -1);
                switch (resultCode) {
                    case VariableActivity.ResultCode_Create:
                        javaGenerator.variable.insert(variable);
                        javaAdapter.notifyItemInserted(position);
                        break;
                    case VariableActivity.ResultCode_Update:
                        javaGenerator.variable.update(position, variable);
                        javaAdapter.notifyItemChanged(position);
                        break;
                    case VariableActivity.ResultCode_Delete:
                        Log.i("JavaAdapter", "count:" +javaAdapter.getItemCount());
                        Log.i("Variables", Arrays.toString(javaGenerator.variable.list().toArray()));
                        javaGenerator.variable.delete(position);
                        javaAdapter.notifyItemRemoved(position);
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateJavaCode(final SetTextable setTextable) {
        progressDialog.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final String content = javaGenerator.toSource();
                String highlighted = javaHighlighter(content);
                final CharSequence charSequence;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    charSequence = Html.fromHtml(highlighted, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    charSequence = Html.fromHtml(highlighted);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Source source = new Source();
                        source.code = content;
                        source.html = charSequence;
                        setTextable.setText(source);
                        progressDialog.hide();
                    }
                });
            }
        });
    }
    private interface SetTextable {
        void setText(Source source);
    }

    private String javaHighlighter(String string) {
        PrettifyHighlighter highlighter = new PrettifyHighlighter();
        String prepare = highlighter.highlight("java", string);

        // to keep indent
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(prepare, "\n ", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            builder.append(token);
            if (token.equals("\n")) {
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
            }
        }
        return builder.toString().replace("\n", "\n<br/>");
    }
}

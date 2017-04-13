package com.example.jobs.solveandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.example.jobs.solveandroid.dialog.VariableDialog;
import com.example.jobs.solveandroid.editor.JavaAdapter;
import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.editor.component.Variable;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;
import com.example.jobs.solveandroid.util.ViewUtil;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.StringTokenizer;

public class EditorActivity extends AppCompatActivity {

    private JavaGenerator javaGenerator = new JavaGenerator("Main");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        javaGenerator.addLocalVariable("name", "sykim -= 1 234");
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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // specify an adapter (see also next example)
        final JavaAdapter adapter = new JavaAdapter(javaGenerator);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");

        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fab_add);
        FloatingActionButton fabVariable = (FloatingActionButton) findViewById(R.id.fab_variable);
        fabVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                new VariableDialog(EditorActivity.this)
                        .setCreateButton(new VariableDialog.OnCreate() {
                            @Override
                            public void createVariable(Variable variable) {
                                javaGenerator.variable.add(variable);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });
        final FloatingActionButton fabPrint = (FloatingActionButton) findViewById(R.id.fab_print);
        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
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
                                Variable variable = getItem(position);
                                if (textView != null && variable != null) {
                                    textView.setText(variable.name);
                                }
                                return root;
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                javaGenerator.command.print(javaGenerator.variable.get(which));
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
                    public void setText(CharSequence charSequence) {
                        LinearLayout linearLayout = new LinearLayout(EditorActivity.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        Space space = new Space(EditorActivity.this);
                        linearLayout.addView(space);
                        space.setMinimumHeight(ViewUtil.dp2Pixel(EditorActivity.this, 20));
                        ScrollView scrollView = new ScrollView(EditorActivity.this);
                        linearLayout.addView(scrollView);
                        TextView textView = new TextView(EditorActivity.this);
                        scrollView.addView(textView);
                        textView.setBackgroundColor(
                                ContextCompat.getColor(EditorActivity.this, R.color.darcula_editor_background)
                        );
                        textView.setPadding(
                                ViewUtil.dp2Pixel(EditorActivity.this, 8),
                                ViewUtil.dp2Pixel(EditorActivity.this, 8),
                                ViewUtil.dp2Pixel(EditorActivity.this, 8),
                                ViewUtil.dp2Pixel(EditorActivity.this, 8)
                        );
                        textView.setText(charSequence);
                        new AlertDialog.Builder(EditorActivity.this)
                                .setTitle("Java Source")
                                .setView(linearLayout)
                                .show();
                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateJavaCode(final SetTextable setTextable) {
        progressDialog.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String content = javaGenerator.toString();
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
                        setTextable.setText(charSequence);
                        progressDialog.hide();
                    }
                });
            }
        });
    }
    private interface SetTextable {
        void setText(CharSequence charSequence);
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

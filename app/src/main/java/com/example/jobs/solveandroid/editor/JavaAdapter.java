package com.example.jobs.solveandroid.editor;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobs.solveandroid.R;
import com.example.jobs.solveandroid.editor.command.Command;
import com.example.jobs.solveandroid.editor.component.Variable;


public class JavaAdapter extends RecyclerView.Adapter {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private static class VariableHolder extends RecyclerView.ViewHolder {
        public static final int TYPE = 10;
        View root;
        TextView typeView;
        TextView nameView;
        TextView valueView;
        public VariableHolder(View root) {
            super(root);
            this.root = root;
            typeView = (TextView) root.findViewById(R.id.type);
            nameView = (TextView) root.findViewById(R.id.name);
            valueView = (TextView) root.findViewById(R.id.value);
        }

        void setOnClickListener(View.OnClickListener onClickListener) {
            root.setOnClickListener(onClickListener);
        }
    }

    private static class CommandHolder extends RecyclerView.ViewHolder {
        public static final int TYPE = 20;

        TextView textView;
        public CommandHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    private JavaGenerator javaGenerator;

    public JavaAdapter(JavaGenerator javaGenerator) {
        this.javaGenerator = javaGenerator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v;
        switch (viewType) {
            case VariableHolder.TYPE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.holder_variable, parent, false);
                return new VariableHolder(v);
            case CommandHolder.TYPE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
                return new CommandHolder((TextView) v);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < javaGenerator.variable.size()) {
            return VariableHolder.TYPE;
        } else {
            return CommandHolder.TYPE;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        System.out.println("create " + position);
        if (holder instanceof VariableHolder) {
            final Variable variable = javaGenerator.variable.get(position);

            VariableHolder variableHolder = (VariableHolder) holder;
            variableHolder.typeView.setText(variable.type.toString());
            variableHolder.nameView.setText(variable.name);
            variableHolder.valueView.setText(variable.toString());
            variableHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Detail")
                            .setMessage("value:" + variable)
                            .show();
                }
            });
        } else if (holder instanceof CommandHolder) {
            int index = position - javaGenerator.variable.size();
            CommandHolder commandHolder = (CommandHolder) holder;
            commandHolder.textView.setText(
                    javaGenerator.command.get(index).toString()
            );
        }
    }

    @Override
    public int getItemCount() {
        return javaGenerator.size();
    }
}

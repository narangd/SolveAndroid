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

    private static final int SpaceType = 0;

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

        View root;
        TextView returnTypeView;
        TextView nameView;
        TextView parameterView;
        public CommandHolder(View root) {
            super(root);
            this.root = root;
            returnTypeView = (TextView) root.findViewById(R.id.return_type);
            nameView = (TextView) root.findViewById(R.id.name);
            parameterView = (TextView) root.findViewById(R.id.parameter);
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
                        .inflate(R.layout.holder_command, parent, false);
                return new CommandHolder(v);
            case SpaceType:
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.holder_fab_space, parent, false);
                return new RecyclerView.ViewHolder(v) {};
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < javaGenerator.variable.size()) {
            return VariableHolder.TYPE;
        } else if (position < javaGenerator.size()){
            return CommandHolder.TYPE;
        } else {
            return SpaceType;
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
            commandHolder.returnTypeView.setText("void");
            commandHolder.nameView.setText(
                    javaGenerator.command.get(index).toString()
            );
            commandHolder.parameterView.setText("-");
            commandHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return javaGenerator.size()+1;
    }
}

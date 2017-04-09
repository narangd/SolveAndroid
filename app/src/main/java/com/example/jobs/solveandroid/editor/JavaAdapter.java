package com.example.jobs.solveandroid.editor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobs.solveandroid.R;
import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;


public class JavaAdapter extends RecyclerView.Adapter {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class VariableHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView typeView;
        TextView nameView;
        TextView valueView;
        public VariableHolder(View root) {
            super(root);
            typeView = (TextView) root.findViewById(R.id.type);
            nameView = (TextView) root.findViewById(R.id.name);
            valueView = (TextView) root.findViewById(R.id.value);
        }
    }

    private JavaGenerator javaGenerator;
    private ArrayList<Variable> variables;

    public JavaAdapter(JavaGenerator javaGenerator) {
        this.javaGenerator = javaGenerator;
        this.variables = new ArrayList<>();
        variables.addAll(javaGenerator.getVariables());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_variable, parent, false);
        return new VariableHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VariableHolder) {
            Variable variable = variables.get(position);

            VariableHolder variableHolder = (VariableHolder) holder;
            variableHolder.typeView.setText(variable.type.toString());
            variableHolder.nameView.setText(variable.name);
            variableHolder.valueView.setText(variable.valueString());
        }
    }

    @Override
    public int getItemCount() {
        return variables.size();
    }
}

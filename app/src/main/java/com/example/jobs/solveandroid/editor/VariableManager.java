package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.adapter.JavaSourceAdapter;
import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class VariableManager implements JavaSourceAdapter {
    private final ArrayList<Variable> variables = new ArrayList<>();

    public void remove(Variable variable) {
        variables.remove(variable);
    }

    public void insert(Variable variable) {
//        if (variableHashSet.(variable) == null) {
//            variableHashSet.put(variable, variable);
//            variables.add(variable);
//        }
        variables.add(variable);
    }

    public void update(int index, Variable variable) {
        System.out.println("origin:" + variables.get(index));
        System.out.println("input:" + variable);
        variables.get(index).copy(variable);
        System.out.println("change:" + index + ", " + variables.get(index));
//        variableHashMap.
//        if (variableHashMap.get(variable.name) != null) {
//            variableHashMap.put(variable.name, variable);
//            variables.get(index).copy(variable);
//            variables.add(variable);
//        }
    }

    public Variable delete(int position) {
        return variables.remove(position);
    }

    public Variable get(int index) {
        return variables.get(index);
    }

    public int size() {
        return variables.size();
    }

    public List<Variable> list() {
        return variables;
    }

//    public String definition() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Variable variable : variables) {
//            stringBuilder
//                    .append("  ")
//                    .append(variable.type)
//                    .append(" ")
//                    .append(variable.name)
//                    .append(" = ")
//                    .append(variable)
//                    .append(";\n");
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public void toSource(StringBuilder builder) {
        for (Variable variable : variables) {
            builder
                    .append("  ")
                    .append(variable.type)
                    .append(" ")
                    .append(variable.name)
                    .append(" = ")
                    .append(variable)
                    .append(";\n");
        }

    }
}

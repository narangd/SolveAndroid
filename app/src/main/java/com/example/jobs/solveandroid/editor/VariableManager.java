package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class VariableManager {
    private final HashMap<String, Variable> variableHashMap = new HashMap<>();
    private final ArrayList<Variable> variableArray = new ArrayList<>();

    public void remove(Variable variable) {
        variableArray.remove(variable);
        variableHashMap.remove(variable.name);
    }

    public void add(Variable variable) {
        if (variableHashMap.get(variable.name) == null) {
            variableHashMap.put(variable.name, variable);
            variableArray.add(variable);
        }
        System.out.println(Arrays.toString(variableHashMap.values().toArray()));
        System.out.println(Arrays.toString(variableArray.toArray()));
    }

    public Variable get(String name) {
        return variableHashMap.get(name);
    }

    public Variable get(int index) {
        return variableArray.get(index);
    }

    public int size() {
        return variableHashMap.size();
    }

    public List<Variable> list() {
        return variableArray;
    }

    public String definition() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Variable variable : variableArray) {
            stringBuilder
                    .append("  ")
                    .append(variable.type)
                    .append(" ")
                    .append(variable.name)
                    .append(" = ")
                    .append(variable)
                    .append(";\n");
        }
        return stringBuilder.toString();
    }
}

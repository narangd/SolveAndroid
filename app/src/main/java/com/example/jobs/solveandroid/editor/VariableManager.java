package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class VariableManager {
    private final ArrayList<Variable> variableArray = new ArrayList<>();

    public void remove(Variable variable) {
        variableArray.remove(variable);
    }

    public void insert(Variable variable) {
//        if (variableHashSet.(variable) == null) {
//            variableHashSet.put(variable, variable);
//            variableArray.add(variable);
//        }
        variableArray.add(variable);
    }

    public void update(int index, Variable variable) {
        System.out.println("origin:" + variableArray.get(index));
        System.out.println("input:" + variable);
        variableArray.get(index).copy(variable);
        System.out.println("change:" + index + ", " + variableArray.get(index));
//        variableHashMap.
//        if (variableHashMap.get(variable.name) != null) {
//            variableHashMap.put(variable.name, variable);
//            variableArray.get(index).copy(variable);
//            variableArray.add(variable);
//        }
    }

//    public Variable get(String name) {
//        return variableHashMap.get(name);
//    }

    public Variable get(int index) {
        return variableArray.get(index);
    }

    public int size() {
        return variableArray.size();
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

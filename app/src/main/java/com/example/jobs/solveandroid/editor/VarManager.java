package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.HashMap;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class VarManager {
    public final HashMap<String, Variable> variables = new HashMap<>();

    public void remove(String name) {
        variables.remove(name);
    }

//    public Collection<Variable> getNames() {
//        return variables;
//    }

    public String definition() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : variables.keySet()) {
            Variable variable = variables.get(key);
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

package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.Collection;
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

    public Collection<Variable> getNames() {
        return variables.values();
    }

    public String definition() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : variables.keySet()) {
            Variable variable = variables.get(name);
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

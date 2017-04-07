package com.example.jobs.solveandroid.editor;

import java.util.HashMap;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class VarManager {
    private HashMap<String, Variable> variables = new HashMap<>();

    public void add(String name, byte value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, int value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, long value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, char value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, String value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, float value) {
        variables.put(name, new Variable(name, value));
    }

    public void add(String name, double value) {
        variables.put(name, new Variable(name, value));
    }

    public void remove(String name) {
        variables.remove(name);
    }

    public Variable get(String name) {
        return variables.get(name);
    }

    public String definition() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : variables.keySet()) {
            Variable variable = variables.get(name);
            stringBuilder
                    .append("  ")
                    .append(variable.type)
                    .append(" ")
                    .append(name)
                    .append(" = ")
                    .append(variable)
                    .append(";\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : variables.keySet()) {
            Variable variable = variables.get(name);
            stringBuilder
                    .append("  ")
                    .append(variable.type)
                    .append(" ")
                    .append(name)
                    .append(" = ")
                    .append(variable)
                    .append(";\n");
        }
        return stringBuilder.toString();
    }
}

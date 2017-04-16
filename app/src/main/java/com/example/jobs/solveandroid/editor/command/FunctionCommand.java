package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

public class FunctionCommand extends Command {
    private String name;
    private Type type;
    Variable[] parameters;
    String parameter = "";

    public FunctionCommand(String name, Type type, Variable... parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;

        boolean first = true;
        for (Variable variable : parameters) {
            if (first) {
                first = false;
            } else {
                parameter += ", ";
            }
            parameter += variable.name;
        }
    }

    @Override
    public String toString() {

        return name + "( "+ parameter + " )";
    }
}

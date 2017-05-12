package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * functoin run
 * f()
 * f(1)
 * f("a")
 * ...
 */
public class FunctionCommand extends Command {
    protected String name;
    protected Type returnType;
    protected Variable[] parameters;
    protected String parameterString = "";

    public FunctionCommand(String name, Type returnType, Variable... parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;

        boolean first = true;
        for (Variable variable : parameters) {
            if (first) {
                first = false;
            } else {
                parameterString += ", ";
            }
            parameterString += variable.name;
        }
    }

    @Override
    public String toString() {

        return name + "( "+ parameterString + " )";
    }

    @Override
    public void toSource(StringBuilder builder) {
        builder.append(name)
                .append("( ")
                .append(parameterString)
                .append(" )");
    }

    @Override
    public void toConsole(StringBuilder builder) {

    }

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public Variable[] getParameters() {
        return parameters;
    }

    public String getParameterString() {
        return parameterString;
    }
}

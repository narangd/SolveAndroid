package com.example.jobs.solveandroid.editor.component;

import com.example.jobs.solveandroid.editor.VarManager;

public class Function {
    private String name;
    private String returnType;
    private Variable[] parameters = null;
    public VarManager variables = new VarManager();

    public Function(String name, String returnType, Variable[] parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }
}

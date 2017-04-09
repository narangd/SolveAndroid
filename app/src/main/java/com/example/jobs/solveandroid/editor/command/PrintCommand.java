package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class PrintCommand extends Command {
    private Variable variable;
    public PrintCommand(Variable variable) {
        this.variable = variable;
    }
    @Override
    public String toString() {
        return "System.out.println( "+ variable.name + " )";
    }
}

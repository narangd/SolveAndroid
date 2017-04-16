package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class PrintCommand extends FunctionCommand {
//    private Variable variable;
    public PrintCommand(Variable variable) {
        super("System.out.println", Type.Void, variable);
//        this.variable = variable;
    }
//    @Override
//    public String toString() {
//        return "System.out.println( "+ variable.name + " )";
//    }
}

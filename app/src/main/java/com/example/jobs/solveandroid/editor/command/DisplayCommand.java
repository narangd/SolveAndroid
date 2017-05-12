package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class DisplayCommand extends FunctionCommand {

    private Variable variable;

    public DisplayCommand(Variable variable) {
        super("Display", Type.Void, variable);
        this.variable = variable;
    }

    @Override
    public void toSource(StringBuilder builder) {
        builder.append("System.out.println( ")
                .append(parameterString)
                .append(" )");
    }

    @Override
    public void toConsole(StringBuilder builder) {
        builder.append(variable.value)
                .append("\n");
    }
}

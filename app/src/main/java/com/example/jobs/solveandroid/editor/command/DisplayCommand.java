package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.JavaSourceAdapter;
import com.example.jobs.solveandroid.editor.Type;
import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class DisplayCommand extends FunctionCommand {
//    private Variable variable;
    private boolean newLine;

    public DisplayCommand(Variable variable, boolean newLine) {
        super("Display" + (newLine?"NewLine":""), Type.Void, variable);
        this.newLine = newLine;
    }

    @Override
    public void toSource(StringBuilder builder) {
        builder.append(newLine ? "System.out.println( " : "System.out.print( ")
                .append(parameterString)
                .append(" )");
    }
}

package com.example.jobs.solveandroid.editor.command;

import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class CommandManager {
    private ArrayList<Command> commands = new ArrayList<>();

    public void print(Variable variable) {
        commands.add(new PrintCommand(variable));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands) {
            stringBuilder
                    .append("  ")
                    .append(command)
                    .append(";\n");
        }
        return stringBuilder.toString();
    }
}

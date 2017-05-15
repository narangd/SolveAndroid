package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.adapter.JavaSourceAdapter;
import com.example.jobs.solveandroid.editor.command.Command;
import com.example.jobs.solveandroid.editor.adapter.ConsoleAdapter;
import com.example.jobs.solveandroid.editor.command.DisplayCommand;
import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class CommandManager implements JavaSourceAdapter, ConsoleAdapter {
    private ArrayList<Command> commands = new ArrayList<>();

    public void print(Variable variable) {
        commands.add(new DisplayCommand(variable));
    }

    public void println() {
        commands.add(new DisplayCommand());
    }

//    public void remove(int index) {
//        commands.remove(index);
//    }

    public int size() {
        return commands.size();
    }

    public Command get(int index) {
        return commands.get(index);
    }

    public List<Command> list() {
        return commands;
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

    @Override
    public void toSource(StringBuilder builder) {
        for (Command command : commands) {
            builder.append("  ");

            command.toSource(builder);

            builder.append(";\n");
        }
    }

    @Override
    public void toConsole(StringBuilder builder) {
        for (Command command : commands) {
            command.toConsole(builder);
        }
    }
}

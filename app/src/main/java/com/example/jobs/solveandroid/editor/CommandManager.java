package com.example.jobs.solveandroid.editor;

import android.util.Log;

import com.example.jobs.solveandroid.editor.command.Command;
import com.example.jobs.solveandroid.editor.command.PrintCommand;
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

//    public void remove(int index) {
//        commands.remove(index);
//    }

    public int size() {
        return commands.size();
    }

    public Command get(int index) {
        return commands.get(index);
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

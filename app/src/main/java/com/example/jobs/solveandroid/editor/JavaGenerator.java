package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.command.CommandManager;
import com.example.jobs.solveandroid.editor.component.Variable;

import java.util.Collection;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class JavaGenerator {
    private String title = "";
    private VarManager variable = new VarManager();
    public final CommandManager command = new CommandManager();

    public JavaGenerator(String title) {
        this.title = title;
    }

    public void addLocalVariable(String name, byte value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, int value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, long value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, char value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, String value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, float value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public void addLocalVariable(String name, double value) {
        variable.variables.put(title+".main."+name, new Variable(name, value));
    }

    public Collection<Variable> getVariables() {
        return variable.getNames();
    }

    @Override
    public final String toString() {
        return "public class " + title + " {\n" +
                " /** main */\n" +
                " public static void main(String[] args) {\n" +
                "  /** variables */\n" +
                variable.definition() +
                "  /** command */\n" +
                command +
                " } /** end of main */\n" +
                "}";
    }
}

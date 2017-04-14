package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.component.Variable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class JavaGenerator {
    private String title = "";
    public final VariableManager variable = new VariableManager();
    public final CommandManager command = new CommandManager();

    public JavaGenerator(String title) {
        this.title = title;
    }

    public void addLocalVariable(String name, byte value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, int value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, long value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, char value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, String value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, float value) {
        variable.add(new Variable(name, value));
    }

    public void addLocalVariable(String name, double value) {
        variable.add(new Variable(name, value));
    }

    public int size() {
        return variable.size() + command.size();
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

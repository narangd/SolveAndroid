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
        variable.insert(new Variable(name, value));
    }

    public void addLocalVariable(String name, int value) {
        variable.insert(new Variable(name, value));
    }

    public void addLocalVariable(String name, long value) {
        variable.insert(new Variable(name, value));
    }

    public void addLocalVariable(String name, char value) {
        variable.insert(new Variable(name, value));
    }

    public void addLocalVariable(String name, String value) {
        variable.insert(new Variable(name, value));
    }

//    public void addLocalVariable(String name, float value) {
//        variable.insert(new Variable(name, value));
//    }

    public void addLocalVariable(String name, double value) {
        variable.insert(new Variable(name, value));
    }

    public int size() {
        return variable.size() + command.size();
    }

    public String toSource() {
        StringBuilder builder = new StringBuilder();
        builder.append("public class ").append(title).append(" {\n")
                .append(" /** main */\n")
                .append(" public static void main(String[] args) {\n")
                .append("  /** variables */\n");

        variable.toSource(builder);

        builder
                .append("  /** command */\n");

        command.toSource(builder);

        builder
                .append(" } /** end of main */\n")
                .append("}");



        return builder.toString();
    }

//    @Override
//    public final String toString() {
//        return "public class " + title + " {\n" +
//                " /** main */\n" +
//                " public static void main(String[] args) {\n" +
//                "  /** variables */\n" +
//                variable.definition() +
//                "  /** command */\n" +
//                command +
//                " } /** end of main */\n" +
//                "}";
//    }
}

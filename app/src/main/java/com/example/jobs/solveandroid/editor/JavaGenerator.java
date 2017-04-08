package com.example.jobs.solveandroid.editor;

import com.example.jobs.solveandroid.editor.command.CommandManager;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class JavaGenerator {
    private String title = "";
    public final VarManager variable = new VarManager();
    public final CommandManager command = new CommandManager();

    public JavaGenerator(String title) {
        this.title = title;
    }

    @Override
    public final String toString() {
        return "public class " + title + " {\r\n" +
                " public static void main(String[] args) {\r\n" +
                "  /** main */\r\n" +
                variable +
                command +
                " }\r\n" +
                "}";
    }
}

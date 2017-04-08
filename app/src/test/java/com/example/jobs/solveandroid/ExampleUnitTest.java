package com.example.jobs.solveandroid;

import android.util.Log;

import com.example.jobs.solveandroid.editor.JavaGenerator;
import com.example.jobs.solveandroid.highlighter.PrettifyHighlighter;

import org.junit.Test;

import java.util.StringTokenizer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void java() {
        JavaGenerator javaGenerator = new JavaGenerator("Main");
        javaGenerator.variable.add("name", "김성용");
        javaGenerator.command.print(javaGenerator.variable.get("name"));
        System.out.println(javaGenerator.toString());
    }

    @Test
    public void string() {
        String input = "public class Main {public static void main(String[] args) {/** main */String name = \"김성용\";System.out.println( name );}}";

        PrettifyHighlighter highlighter = new PrettifyHighlighter();
        String prepare = highlighter.highlight("java", input);

        int depth = 0;
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(prepare, "{}", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            System.out.printf("%s \n", token);
            builder.append(token);
            switch (token) {
                case "{":
                    depth++;
                    break;
                case "}":
                    depth--;
                    break;
                // TODO: not work
                case "\n":
                    for (int i=0; i<depth; i++) {
                        builder.append("&nbsp;");
                    }
                    break;
            }
        }
        System.out.println(builder.toString().replace("\n", "\n<br/>"));
    }
}
//public class Main {public static void main(String[] args) {/** main */String name = "김성용";System.out.println( name );}}
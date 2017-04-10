package com.example.jobs.solveandroid.highlighter;

import android.graphics.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

/**
 * Created by jobs on 2017. 4. 7..
 */

public class PrettifyHighlighter {
    private static final Map<String, String> COLORS = buildColorsMap();

    private static final String FONT_PATTERN = "<font color=\"%s\">%s</font>";

    private final Parser parser = new PrettifyParser();

    public String highlight(String fileExtension, String sourceCode) {
        StringBuilder highlighted = new StringBuilder();
        List<ParseResult> results = parser.parse(fileExtension, sourceCode);
        for(ParseResult result : results){
            String type = result.getStyleKeys().get(0);
            String content = sourceCode.substring(result.getOffset(), result.getOffset() + result.getLength());
            highlighted.append(String.format(FONT_PATTERN, getColor(type), content));
        }
        return highlighted.toString();
    }

    private String getColor(String type){
        return COLORS.containsKey(type) ? COLORS.get(type) : COLORS.get("pln");
    }

    private static Map<String, String> buildColorsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("typ", "#87cefa");
        map.put("kwd", "#CC7832");
        map.put("lit", "#ffff00");
        map.put("com", "#808080");
        map.put("str", "#008000");
        map.put("pun", "#E8BF6A");
        map.put("pln", "#A9B7C6");
        return map;
    }
}

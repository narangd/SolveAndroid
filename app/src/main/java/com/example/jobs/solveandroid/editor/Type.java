package com.example.jobs.solveandroid.editor;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public enum Type {
    Byte("byte"),
    Short("short"),
    Integer("int"),
    Long("long"),
    Character("char"),
    String("String"),
    Float("float"),
    Double("double"),
    Boolean("boolean"),
    Object("Object"),
    ;
    private String name;
    Type(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return name;
    }
}

package com.example.jobs.solveandroid.editor;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class Variable {
    public final String name;
    public final Type type;
    private Object value;

    public Variable(String name, byte integer) {
        this.name = name;
        type = Type.Byte;
        value = integer;
    }

    public Variable(String name, int integer) {
        this.name = name;
        type = Type.Integer;
        value = integer;
    }

    public Variable(String name, long integer) {
        this.name = name;
        type = Type.Long;
        value = integer;
    }

    public Variable(String name, char character) {
        this.name = name;
        type = Type.Character;
        value = character;
    }

    public Variable(String name, String string) {
        this.name = name;
        type = Type.String;
        value = string;
    }

    public Variable(String name, float _float) {
        this.name = name;
        type = Type.Float;
        value = _float;
    }

    public Variable(String name, double _float) {
        this.name = name;
        type = Type.Double;
        value = _float;
    }

    public int getInteger() {
        return Integer.parseInt(value.toString());
    }

    @Override
    public String toString() {
        switch (type) {
            case Character:
                return "'"+ value +"'";
            case String:
                return "\""+ value +"\"";
            case Byte:
            case Integer:
            case Long:
            case Float:
            case Double:
            default:
                return value.toString();
        }
    }
}

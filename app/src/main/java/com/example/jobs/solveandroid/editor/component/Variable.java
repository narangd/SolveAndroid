package com.example.jobs.solveandroid.editor.component;

import android.support.annotation.NonNull;

import com.example.jobs.solveandroid.editor.Type;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class Variable implements Comparable<Variable> {
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

    public byte valueByte() {
        return Byte.parseByte(value.toString());
    }

    public int valueInteger() {
        return Integer.parseInt(value.toString());
    }

    public long valueLong() {
        return Long.parseLong(value.toString());
    }

    public char valueCharacter() {
        return value.toString().charAt(0);
    }

    public String valueString() {
        return value.toString();
    }

    public float valueFloat() {
        return Float.parseFloat(value.toString());
    }

    public double valueDouble() {
        return Double.parseDouble(value.toString());
    }

    @Override
    public String toString() {
        switch (type) {
            case Object:
                return "null";
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

    public static String getDefault(Type type) {
        switch (type) {
            case Character:
                return "'\0'";
            case String:
                return "\"\"";
            case Byte:
            case Integer:
            case Long:
                return "0";
            case Float:
            case Double:
                return "0.0";
            case Object:
            default:
                return "null";
        }
    }

    @Override
    public int compareTo(@NonNull Variable o) {
        return name.compareTo(o.name);
    }
}

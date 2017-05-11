package com.example.jobs.solveandroid.editor.component;

import android.support.annotation.NonNull;

import com.example.jobs.solveandroid.editor.Type;

import java.io.Serializable;

/**
 * @author sykim
 * @date 2017. 04. 06
 */
public class Variable implements Serializable {
    public String name;
    public Type type;
    public Object value;

//    public Variable(String name, byte integer) {
//        this.name = name;
//        type = Type.Byte;
//        value = integer;
//    }
//
//    public Variable(String name, short integer) {
//        this.name = name;
//        type = Type.Short;
//        value = integer;
//    }

    public Variable(String name, int integer) {
        this.name = name;
        type = Type.Integer;
        value = integer;
    }

//    public Variable(String name, long integer) {
//        this.name = name;
//        type = Type.Long;
//        value = integer;
//    }

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

//    public Variable(String name, float _float) {
//        this.name = name;
//        type = Type.Float;
//        value = _float;
//    }
//
//    public Variable(String name, double _float) {
//        this.name = name;
//        type = Type.Double;
//        value = _float;
//    }

    public Variable(String name, double _float) {
        this.name = name;
        type = Type.Float;
        value = _float;
    }

    public Variable(String name, boolean _boolean) {
        this.name = name;
        type = Type.Boolean;
        value = _boolean;
    }

//    public byte valueByte() {
//        return Byte.parseByte(value.toString());
//    }

//    public short valueShort() {
//        return Short.parseShort(value.toString());
//    }

    public int valueInteger() {
        return Integer.parseInt(value.toString());
    }

//    public long valueLong() {
//        return Long.parseLong(value.toString());
//    }

    public char valueCharacter() {
        return value.toString().charAt(0);
    }

    public String valueString() {
        return value.toString();
    }

//    public float valueFloat() {
//        return Float.parseFloat(value.toString());
//    }
//
//    public double valueDouble() {
//        return Double.parseDouble(value.toString());
//    }

    public double valueFloat() {
        return Double.parseDouble(value.toString());
    }

    public boolean valueBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    @Override
    public String toString() {
        switch (type) {
//            case Object:
//                return "null";
            case Character:
                return "'"+ value +"'";
            case String:
                return "\""+ value +"\"";
//            case Byte:
//            case Short:
            case Integer:
//            case Long:
            case Float:
//            case Double:
            case Boolean:
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
//            case Byte:
//            case Short:
            case Integer:
//            case Long:
                return "0";
            case Float:
//            case Double:
                return "0.0";
            case Boolean:
                return "false";
//            case Object:
            default:
                return "null";
        }
    }

    public static Variable fromType(Type type, String name, String value) {
        Variable variable;
        switch (type) {
//            case Byte:
//                variable = new Variable(name, Byte.parseByte(value));
//                break;
//            case Short:
//                variable = new Variable(name, Short.parseShort(value));
//                break;
            case Integer:
                variable = new Variable(name, Integer.parseInt(value));
                break;
//            case Long:
//                variable = new Variable(name, Long.parseLong(value));
//                break;
            case Float:
                variable = new Variable(name, Double.parseDouble(value));
                break;
//            case Double:
//                variable = new Variable(name, Double.parseDouble(value));
//                break;
            case Character:
                variable = new Variable(name, value.charAt(0));
                break;
            case Boolean:
                variable = new Variable(name, Boolean.parseBoolean(value));
                break;
            case String:
            default:
                variable = new Variable(name, value);
        }
        return variable;
    }

//    @Override
//    public int compareTo(@NonNull Variable o) {
//        return name.compareTo(o.name);
//    }

    public void copy(Variable to) {
        type = to.type;
        name = to.name;
        value = to.value;
    }
}

package com.example;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MyClass {
     final public static Test test = new Test("123");

    public static void main(String[] args) throws Exception {
        Field field = MyClass.class.getDeclaredField("test");
        field.setAccessible(true);
        field.set(null,new Test("111"));
//        setFinalStatic(field,new Test("111"));
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}

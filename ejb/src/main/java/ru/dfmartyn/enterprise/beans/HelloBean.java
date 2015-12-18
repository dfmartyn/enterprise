package ru.dfmartyn.enterprise.beans;

import java.io.Serializable;

/**
 * @author Dmitriy Martynov
 */
public class HelloBean implements Serializable {

    private long id = System.currentTimeMillis();

    public HelloBean() {
        System.out.println("Simple bean constructor. Id = " + id);
    }

    public String sayHello() {
        System.out.println("Simple bean hello method. Id = " + id);
        System.out.println("hello!");
        return "Hello";
    }
}

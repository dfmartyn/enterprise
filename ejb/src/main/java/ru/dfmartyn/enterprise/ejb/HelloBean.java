package ru.dfmartyn.enterprise.ejb;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @author Dmitriy Martynov
 */
public class HelloBean implements Serializable{

    public String sayHello(){
        System.out.println("hello!");
        return "Hello";
    }
}
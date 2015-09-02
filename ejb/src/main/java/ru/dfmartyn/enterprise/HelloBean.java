package ru.dfmartyn.enterprise;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @author Dmitriy Martynov
 */
@SessionScoped
public class HelloBean implements Serializable{

    public String sayHello(){
        System.out.println("hello!");
        return "Hello";
    }
}

package ru.dfmartyn.enterprise.beans;

import org.jboss.logging.Logger;

import java.io.Serializable;

/**
 * @author Dmitriy Martynov
 */
public class HelloBean implements Serializable {

    private static final Logger log = Logger.getLogger(HelloBean.class);

    private long id = System.currentTimeMillis();

    public HelloBean() {
        System.out.println("Simple bean constructor. Id = " + id);
    }

    public String sayHello() {
        log.info("Simple bean hello method. Id = " + id);
        log.info("hello!");
        return "Hello";
    }
}

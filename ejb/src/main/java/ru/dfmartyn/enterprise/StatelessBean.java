package ru.dfmartyn.enterprise;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * @author Dmitriy Martynov
 */
@Stateless(name = "stateless")
public class StatelessBean implements LocalInterface, RemoteInterface {


    @PostConstruct
    void postConstruct() {
        System.out.println("Post Construct");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("Pre Destroy");
    }

    @Override
    public String helloWorld() {
        return "Hello";
    }
}

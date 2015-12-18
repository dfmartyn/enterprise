package ru.dfmartyn.enterprise.ejb;

import ru.dfmartyn.enterprise.beans.HelloBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Dmitriy Martynov
 */
@Stateless(name = "stateless")
public class StatelessBean implements LocalInterface, RemoteInterface {

    private long id = System.currentTimeMillis();

    @Inject
    private HelloBean helloBean;

    /**
     * Default constructor
     */
    public StatelessBean() {
        System.out.println("Stateless constructor. Id = " + id);
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("Post Construct. Id = " + id);
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("Pre Destroy. Id = " + id);
    }

    @Override
    public String helloWorld() {
        helloBean.sayHello();
        return "Hello";
    }
}

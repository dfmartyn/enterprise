package ru.dfmartyn.enterprise.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Dmitriy Martynov
 */
@Stateless(name = "mybean")
public class StatelessBean implements LocalInterface, RemoteInterface {

    @Inject
    private HelloBean helloBean;

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
        helloBean.sayHello();
        return "Hello";
    }
}

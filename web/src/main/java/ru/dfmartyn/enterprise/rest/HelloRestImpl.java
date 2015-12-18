package ru.dfmartyn.enterprise.rest;

import ru.dfmartyn.enterprise.ejb.LocalInterface;

import javax.ejb.EJB;

@SuppressWarnings("unused")
public class HelloRestImpl implements HelloRest {

    private long id = System.currentTimeMillis();

    @EJB
    private LocalInterface stateless;

    public HelloRestImpl() {
        System.out.println("Service constructor. Id = " + id);
    }

    @Override
    public String sayHello() {
        return stateless.helloWorld();
    }
}

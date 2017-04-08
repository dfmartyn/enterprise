package ru.dfmartyn.enterprise.rest;

import ru.dfmartyn.enterprise.dto.ValueText;
import ru.dfmartyn.enterprise.ejb.LocalInterface;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

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

    @Override
    public ValueText getValueText() {
        ValueText result = new ValueText();
        result.setId(1);
        result.setName("name");
        return result;
    }

    @Override
    public Response setValueText(ValueText valueText) {
        System.out.println("Получен объект " + valueText);
        return Response.ok(valueText).build();
    }


}

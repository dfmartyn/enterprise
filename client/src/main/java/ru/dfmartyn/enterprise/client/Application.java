package ru.dfmartyn.enterprise.client;

import ru.dfmartyn.enterprise.client.caller.EJBCaller;
import ru.dfmartyn.enterprise.client.sender.MessageSender;

/**
 * @author Dmitriy Martynov
 */
public class Application {

    public static void main(String[] args) {

        EJBCaller.invokeStatelessEJB();

        MessageSender.sendMessage();
    }
}

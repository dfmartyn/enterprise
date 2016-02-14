package ru.dfmartyn.enterprise.jms;

import javax.ejb.Remote;

@Remote
public interface SendMessageService {

    void sendAndUpdate(String text) throws Exception;

    void createTable() throws Exception;

}
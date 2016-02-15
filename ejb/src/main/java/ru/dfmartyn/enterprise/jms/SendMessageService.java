package ru.dfmartyn.enterprise.jms;

import javax.jms.JMSException;
import java.sql.SQLException;

public interface SendMessageService {

    void sendAndUpdate(String text) throws SQLException, JMSException;

    void createTable() throws SQLException;

}
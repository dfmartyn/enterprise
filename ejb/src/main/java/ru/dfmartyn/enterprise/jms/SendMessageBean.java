package ru.dfmartyn.enterprise.jms;


import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.PreparedStatement;

/**
 * An EJB which sends a JMS message and update a JDBC table in the same transaction.
 */
@Stateless(name = "message")
public class SendMessageBean implements SendMessageService {

    private static final String TABLE = "hornetq_example";

    public void createTable() throws Exception {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:jboss/datasources/ExampleDS");
        java.sql.Connection con = ds.getConnection();

        // check if the table exists:
        boolean createTable = false;
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM " + SendMessageBean.TABLE + ";");
            pr.executeQuery();
            pr.close();
        } catch (Exception e) {
            createTable = true;
        }

        if (createTable) {
            PreparedStatement pr = con.prepareStatement("CREATE TABLE " + SendMessageBean.TABLE +
                    "(id VARCHAR(100), text VARCHAR(100));");
            pr.execute();
            pr.close();
            System.out.println("Table " + SendMessageBean.TABLE + " created");
        }

        con.close();
    }

    public void sendAndUpdate(final String text) throws Exception {
        InitialContext ic = null;
        Connection jmsConnection = null;
        java.sql.Connection jdbcConnection = null;

        try {
            // Step 1. Lookup the initial context
            ic = new InitialContext();

            // JMS operations

            // Step 2. Look up the XA Connection Factory
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("java:/JmsXA");

            // Step 3. Look up the Queue
            Queue queue = (Queue) ic.lookup("queue/testQueue");

            // Step 4. Create a connection, a session and a message producer for the queue
            jmsConnection = cf.createConnection();
            Session session = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);

            // Step 5. Create a Text Message
            TextMessage message = session.createTextMessage(text);

            // Step 6. Send The Text Message
            messageProducer.send(message);
            System.out.println("Sent message: " + message.getText() + "(" + message.getJMSMessageID() + ")");

            // DB operations

            // Step 7. Look up the XA Data Source
            DataSource ds = (DataSource) ic.lookup("java:jboss/datasources/XADS");

            // Step 8. Retrieve the JDBC connection
            jdbcConnection = ds.getConnection();

            // Step 9. Create the prepared statement to insert the text and the message's ID in the table
            PreparedStatement pr = jdbcConnection.prepareStatement("INSERT INTO " + SendMessageBean.TABLE +
                    " (id, text) VALUES ('" +
                    message.getJMSMessageID() +
                    "', '" +
                    text +
                    "');");

            // Step 10. execute the prepared statement
            pr.execute();

            // Step 11. close the prepared statement
            pr.close();
        } finally {
            // Step 12. Be sure to close all resources!
            if (ic != null) {
                ic.close();
            }
            if (jmsConnection != null) {
                jmsConnection.close();
            }
            if (jdbcConnection != null) {
                jdbcConnection.close();
            }
        }
    }
}


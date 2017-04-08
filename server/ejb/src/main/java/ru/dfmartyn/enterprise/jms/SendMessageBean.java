package ru.dfmartyn.enterprise.jms;


import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An EJB which sends a JMS message and update a JDBC table in the same transaction.
 */
@Remote(SendMessageService.class)
@Stateless(name = "message")
public class SendMessageBean implements SendMessageService {

    private static final Logger log = Logger.getLogger(SendMessageBean.class);

    private static final String TABLE = "hornetq_example";

    @Resource(mappedName = "java:jboss/datasources/ExampleDS")
    private DataSource dataSource;

    @Resource(mappedName = "java:jboss/datasources/XADS")
    private DataSource xaDataSource;

    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/jms/queue/testQueue")
    private Queue queue;

    public void createTable() throws SQLException {

        // check if the table exists:
        boolean createTable = false;

        try (java.sql.Connection connnection = dataSource.getConnection()) {

            try (PreparedStatement testStatement = connnection.prepareStatement("SELECT * FROM " + SendMessageBean.TABLE + ";")) {
                testStatement.executeQuery();
            } catch (SQLException e) {
                createTable = true;
            }

            if (createTable) {
                PreparedStatement createTableStatement = connnection.prepareStatement(
                        "CREATE TABLE " + SendMessageBean.TABLE + "(id VARCHAR(100), text VARCHAR(100));");
                createTableStatement.execute();
                createTableStatement.close();
                log.info("Table " + SendMessageBean.TABLE + " created");
            }
        }
    }

    public void sendAndUpdate(final String text) throws JMSException, SQLException {
        try (Connection jmsConnection = connectionFactory.createConnection();
             java.sql.Connection jdbcConnection = xaDataSource.getConnection();
             PreparedStatement preparedStatement = jdbcConnection.prepareStatement(
                     "INSERT INTO " + SendMessageBean.TABLE + " (id, text) VALUES (?, ?);")
        ) {
            // Create a connection, a session and a message producer for the queue
            Session session = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);

            // Create a Text Message
            TextMessage message = session.createTextMessage(text);

            // Send The Text Message
            messageProducer.send(message);
            log.info("Sent message: " + message.getText() + "(" + message.getJMSMessageID() + ")");

            // execute the prepared statement
            preparedStatement.setString(1, message.getJMSMessageID());
            preparedStatement.setString(2, text);
            preparedStatement.execute();
        }
    }
}


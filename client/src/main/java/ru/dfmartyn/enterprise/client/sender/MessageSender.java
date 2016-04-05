package ru.dfmartyn.enterprise.client.sender;

import org.jboss.logging.Logger;
import ru.dfmartyn.enterprise.jms.SendMessageService;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class MessageSender {

    private static final Logger log = Logger.getLogger(MessageSender.class);

    public static void sendMessage() {
        final String USERNAME = "guest";
        final String PASSWORD = "password";
        // For JBoss 7
//        final String PROVIDER_URL = "remote://localhost:4447";
        // For Wildfly 10
        final String PROVIDER_URL = "http-remoting://localhost:8080";
        final String JNDI_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
        Context context = null;
        Connection connection = null;
        Session session = null;
        try {
            // Step 1. Create an initial context to perform the JNDI lookup.
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            prop.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
            prop.put(Context.PROVIDER_URL, PROVIDER_URL);
            prop.put(Context.SECURITY_PRINCIPAL, USERNAME);
            prop.put(Context.SECURITY_CREDENTIALS, PASSWORD);

            context = new InitialContext(prop);

            // Step 2. Lookup the EJB
            SendMessageService service = (SendMessageService) context
                    .lookup("ejb:enterprise/ejb/message!ru.dfmartyn.enterprise.jms.SendMessageService");

            // Step 3. Create the DB table which will be updated
            service.createTable();

            // Step 4. Invoke the sendAndUpdate method
            service.sendAndUpdate("This is a text message");
            log.info("invoked the EJB service");

            // Step 5. Lookup the JMS connection factory
            ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");

            // Step 6. Lookup the queue
            Queue queue = (Queue) context.lookup("jms/queue/testQueue");

            // Step 7. Create a connection, a session and a message consumer for the queue
            connection = cf.createConnection(USERNAME, PASSWORD);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);

            // Step 8. Start the connection
            connection.start();

            // Step 9. Receive the message sent by the EJB
            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            log.info("Received message: " + messageReceived.getText() +
                    " (" +
                    messageReceived.getJMSMessageID() +
                    ")");
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        } finally {
            // Step 10. Be sure to close the resources!
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.error(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    log.error(e.getMessage());
                }
            }
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}

package ru.dfmartyn.enterprise.client;

import ru.dfmartyn.enterprise.ejb.RemoteInterface;
import ru.dfmartyn.enterprise.jms.SendMessageService;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dmitriy Martynov
 */
public class Application {

    private static final Logger log = Logger.getLogger(Application.class.getName());

    private final static String USERNAME = "guest";
    private final static String PASSWORD = "password";
    private final static String PROVIDER_URL = "remote://10.10.4.149:4447";
    private final static String JNDI_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";

    public static void main(String[] args) {

        invokeStatelessEJB();

        sendMessage();
    }

    private static void invokeStatelessEJB() {
        Context context = null;
        try {
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            context = new InitialContext(prop);
            RemoteInterface ejb = (RemoteInterface)context.lookup(
                    "ejb:enterprise/ejb/stateless!ru.dfmartyn.enterprise.ejb.RemoteInterface");
            log.log(Level.INFO, ejb.helloWorld());
        } catch (NamingException e) {
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }

    private static void sendMessage() {
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
            log.log(Level.INFO, "invoked the EJB service");

            // Step 5. Lookup the JMS connection factory
            ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");

            // Step 6. Lookup the queue
            Queue queue = (Queue) context.lookup("jms/queue/testQueue");

            // Step 7. Create a connection, a session and a message consumer for the queue
            connection = cf.createConnection("guest", "password");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);

            // Step 8. Start the connection
            connection.start();

            // Step 9. Receive the message sent by the EJB
            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            log.log(Level.INFO, "Received message: " + messageReceived.getText() +
                    " (" +
                    messageReceived.getJMSMessageID() +
                    ")");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            // Step 10. Be sure to close the resources!
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }
}

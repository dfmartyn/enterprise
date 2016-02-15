package ru.dfmartyn.enterprise.client.caller;

import org.jboss.logging.Logger;
import ru.dfmartyn.enterprise.ejb.RemoteInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBCaller {

    private static final Logger log = Logger.getLogger(EJBCaller.class);


    public static void invokeStatelessEJB() {
        Context context = null;
        try {
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            context = new InitialContext(prop);
            RemoteInterface ejb = (RemoteInterface) context.lookup(
                    "ejb:enterprise/ejb/stateless!ru.dfmartyn.enterprise.ejb.RemoteInterface");
            log.info(ejb.helloWorld());
        } catch (NamingException e) {
            log.error(e.getMessage());
        } finally {
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

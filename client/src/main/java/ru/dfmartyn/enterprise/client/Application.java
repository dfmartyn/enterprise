package ru.dfmartyn.enterprise.client;

import ru.dfmartyn.enterprise.ejb.RemoteInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * @author Dmitriy Martynov
 */
public class Application {

    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(prop);
            RemoteInterface ejb = (RemoteInterface)context.lookup(
                    "ejb:enterprise/ejb/mybean!ru.dfmartyn.enterprise.ejb.RemoteInterface");
            System.out.println(ejb.helloWorld());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package ru.dfmartyn.enterprise.ejb;

import org.jboss.logging.Logger;
import ru.dfmartyn.enterprise.beans.HelloBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Dmitriy Martynov
 */
@Remote(RemoteInterface.class)
@Local(LocalInterface.class)
@Stateless(name = "stateless")
public class StatelessBean implements LocalInterface, RemoteInterface {

    private static final Logger log = Logger.getLogger(StatelessBean.class);

    private long id = System.currentTimeMillis();

    @Inject
    private HelloBean helloBean;

    /**
     * Default constructor
     */
    public StatelessBean() {
        log.info("Stateless constructor. Id = " + id);
    }

    @PostConstruct
    void postConstruct() {
        log.info("Post Construct. Id = " + id);
    }

    @PreDestroy
    void preDestroy() {
        log.info("Pre Destroy. Id = " + id);
    }

    @Override
    public String helloWorld() {
        helloBean.sayHello();
        return "Hello";
    }
}

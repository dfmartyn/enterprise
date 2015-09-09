package ru.dfmartyn.enterprise.ejb;

import javax.ejb.Remote;

/**
 * @author Dmitriy Martynov
 */
@Remote
public interface RemoteInterface {

    String helloWorld();
}

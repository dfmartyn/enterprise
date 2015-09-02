package ru.dfmartyn.enterprise;

import javax.ejb.Remote;

/**
 * @author Dmitriy Martynov
 */
@Remote
public interface RemoteInterface {

    String helloWorld();
}

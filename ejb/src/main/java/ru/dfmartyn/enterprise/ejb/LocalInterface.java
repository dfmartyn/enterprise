package ru.dfmartyn.enterprise.ejb;

import javax.ejb.Local;

/**
 * @author Dmitriy Martynov
 */
@Local
public interface LocalInterface {

    String helloWorld();
}

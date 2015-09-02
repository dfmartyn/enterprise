package ru.dfmartyn.enterprise;

import javax.ejb.Local;

/**
 * @author Dmitriy Martynov
 */
@Local
public interface LocalInterface {

    String helloWorld();
}

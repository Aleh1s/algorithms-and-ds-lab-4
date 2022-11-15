package org.example.algorithm.bee.factory;

import org.example.algorithm.bee.BeeColony;
import org.example.algorithm.bee.factory.impl.BeeColonyFactoryImpl;
import org.example.exception.BeeColonyFactoryException;

public abstract class BeeColonyFactory {

    public static BeeColonyFactory newInstance() {
        return new BeeColonyFactoryImpl();
    }
    public abstract BeeColony getBeeColony() throws BeeColonyFactoryException;

}

package org.example.algorithm.bee.factory.impl;

import org.example.algorithm.bee.BeeColony;
import org.example.algorithm.bee.factory.BeeColonyFactory;
import org.example.exception.BeeColonyFactoryImplException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.example.constant.Constant.*;

public class BeeColonyFactoryImpl extends BeeColonyFactory {
    @Override
    public BeeColony getBeeColony() throws BeeColonyFactoryImplException {
        Properties props = new Properties();
        BeeColony beeColony;
        try {
            props.load(new FileReader(APPLICATION_PROPERTIES));
            int numberOfEmployedBees = Integer.parseInt(props.getProperty(NUMBER_OF_EMPLOYED_BEES));
            int numberOfScoutBees = Integer.parseInt(props.getProperty(NUMBER_OF_SCOUT_BEES));
            int numberOfIterations = Integer.parseInt(props.getProperty(NUMBER_OF_ITERATIONS));
            int precision = Integer.parseInt(props.getProperty(PRECISION));
            beeColony = new BeeColony(numberOfEmployedBees, numberOfScoutBees, numberOfIterations, precision);
        } catch (IOException e) {
            throw new BeeColonyFactoryImplException(e);
        }
        return beeColony;
    }
}

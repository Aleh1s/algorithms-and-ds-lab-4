package org.example.algorithm.bee.factory.impl;

import org.example.algorithm.bee.BeeColony;
import org.example.algorithm.bee.factory.BeeColonyFactory;
import org.example.exception.BeeColonyFactoryImplException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BeeColonyFactoryImpl extends BeeColonyFactory {
    @Override
    public BeeColony getBeeColony() throws BeeColonyFactoryImplException {
        Properties props = new Properties();
        BeeColony beeColony;
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
            int numberOfEmployedBees = Integer.parseInt(props.getProperty("number.of.employed.bees"));
            int numberOfScoutBees = Integer.parseInt(props.getProperty("number.of.scout.bees"));
            beeColony = new BeeColony(numberOfEmployedBees, numberOfScoutBees);
        } catch (IOException e) {
            throw new BeeColonyFactoryImplException(e);
        }
        return beeColony;
    }
}

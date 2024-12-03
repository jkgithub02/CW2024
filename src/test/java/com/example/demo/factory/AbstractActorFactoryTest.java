package com.example.demo.factory;

import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.TestActor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AbstractActorFactoryTest extends JavaFXTest {

    @Test
    void testCreateActor() {
        AbstractActorFactory factory = new AbstractActorFactory(50) {
            @Override
            public ActiveActorDestructible createActor(double x, double y) {
                return new TestActor(ImageTestConfig.IMAGENAME, 100, x, y);
            }
        };
        ActiveActorDestructible actor = factory.createActor(100, 200);
        assertNotNull(actor);
    }
}
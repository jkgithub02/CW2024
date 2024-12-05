package com.example.demo.actors.factory;

import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.TestActor;
import com.example.demo.actors.factory.ActorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActorFactoryTest extends JavaFXTest {

    @Test
    void testCreateActor() {
        ActorFactory factory = new ActorFactory() {
            @Override
            public ActiveActorDestructible createActor(double x, double y) {
                return new TestActor(ImageTestConfig.IMAGENAME, 100, x, y);
            }
        };
        ActiveActorDestructible actor = factory.createActor(100, 200);
        assertNotNull(actor);
    }
}
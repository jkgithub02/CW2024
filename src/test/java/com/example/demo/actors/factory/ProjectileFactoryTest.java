package com.example.demo.actors.factory;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.ProjectileFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectileFactoryTest extends JavaFXTest {

    @Test
    void testCreateProjectile() {
        ProjectileFactory factory = new ProjectileFactory(ProjectileFactory.ProjectileType.USER);
        ActiveActorDestructible projectile = factory.createActor(100, 200);
        assertNotNull(projectile);
    }
}
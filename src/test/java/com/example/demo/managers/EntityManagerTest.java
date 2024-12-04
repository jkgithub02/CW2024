package com.example.demo.managers;

import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.TestActor;
import com.example.demo.actors.projectiles.TestProjectile;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerTest extends JavaFXTest {
    private EntityManager entityManager;
    private Group root;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            root = new Group();
            entityManager = EntityManager.getInstance(root);
            entityManager.reset();
        });
        waitForFxEvents();
    }

    private void waitForFxEvents() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectileManagement() {
        Platform.runLater(() -> {
            ActiveActorDestructible projectile = new TestProjectile(ImageTestConfig.IMAGENAME, 1, 1,1);
            entityManager.addEnemyProjectile(projectile);

            assertEquals(1, entityManager.getEnemyProjectiles().size(), "Should have one enemy projectile");
            assertTrue(root.getChildren().contains(projectile), "Projectile should be added to root");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateRoot() {
        Platform.runLater(() -> {
            Group newRoot = new Group();
            entityManager.updateRoot(newRoot);

            // Add a new entity to verify it's added to the new root
            ActiveActorDestructible enemy = new TestActor(ImageTestConfig.IMAGENAME, 1, 1,1);
            entityManager.addEnemyUnit(enemy);

            assertTrue(newRoot.getChildren().contains(enemy), "Entity should be added to new root");
            assertFalse(root.getChildren().contains(enemy), "Entity should not be in old root");
        });
        waitForFxEvents();
    }

}
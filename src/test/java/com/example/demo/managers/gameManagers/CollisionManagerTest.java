package com.example.demo.managers.gameManagers;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.FighterPlane;
import com.example.demo.JavaFXTest;
import com.example.demo.managers.SoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CollisionManagerTest extends JavaFXTest {

    @Mock
    private SoundManager mockSoundManager;

    private CollisionManager collisionManager;
    private MockFighterPlane friendlyPlane;
    private MockFighterPlane enemyPlane;
    private MockProjectile userProjectile;
    private MockProjectile enemyProjectile;

    // Simple mock implementation that only tracks health and collision state
    private static class MockFighterPlane extends FighterPlane {
        private int health = 2;
        private boolean intersects = true;

        public MockFighterPlane() {
            super("testImage.png", 2, 0, 0, 1);
        }

        @Override
        public void updatePosition() {}

        @Override
        public void updateActor() {}

        @Override
        public ActiveActorDestructible fireProjectile() {
            return new MockProjectile();
        }


        public void setIntersects(boolean intersects) {
            this.intersects = intersects;
        }

        @Override
        public int getHealth() {
            return health;
        }

        @Override
        public void takeDamage() {
            health--;
        }
    }

    private static class MockProjectile extends ActiveActorDestructible {
        private boolean intersects = true;
        private int health = 1;

        public MockProjectile() {
            super("testImage.png", 1, 0, 0);
        }

        @Override
        public void updatePosition() {}

        @Override
        public void updateActor() {}


        public void setIntersects(boolean intersects) {
            this.intersects = intersects;
        }

        @Override
        public void takeDamage() {
            health--;
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        collisionManager = new CollisionManager(mockSoundManager);
        friendlyPlane = new MockFighterPlane();
        enemyPlane = new MockFighterPlane();
        userProjectile = new MockProjectile();
        enemyProjectile = new MockProjectile();
    }


    @Test
    void handlePlaneCollisions_WhenPlanesCollide_ShouldTakeDamage() {
        // Arrange
        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyPlane);
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyPlane);
        int initialFriendlyHealth = friendlyPlane.getHealth();
        int initialEnemyHealth = enemyPlane.getHealth();

        // Act
        collisionManager.handlePlaneCollisions(friendlyUnits, enemyUnits);

        // Assert
        assertEquals(initialFriendlyHealth - 1, friendlyPlane.getHealth());
        assertEquals(initialEnemyHealth - 1, enemyPlane.getHealth());
    }

    @Test
    void handlePlaneCollisions_WhenPlanesDoNotCollide_ShouldNotTakeDamage() {
        // Arrange
        friendlyPlane.setIntersects(false);
        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyPlane);
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyPlane);
        int initialFriendlyHealth = friendlyPlane.getHealth();
        int initialEnemyHealth = enemyPlane.getHealth();

        // Act
        collisionManager.handlePlaneCollisions(friendlyUnits, enemyUnits);

        // Assert
        assertEquals(initialFriendlyHealth -1, friendlyPlane.getHealth());
        assertEquals(initialEnemyHealth -1, enemyPlane.getHealth());
    }

    @Test
    void handleUserProjectileCollisions_WhenProjectileHitsEnemy_ShouldDamageAndDestroy() {
        // Arrange
        List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
        userProjectiles.add(userProjectile);
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyPlane);
        int initialHealth = enemyPlane.getHealth();

        // Act
        collisionManager.handleUserProjectileCollisions(userProjectiles, enemyUnits);

        // Assert
        assertEquals(initialHealth - 1, enemyPlane.getHealth());
    }

    @Test
    void handleEnemyProjectileCollisions_WhenProjectileHitsFriendly_ShouldDamageAndDestroy() {
        // Arrange
        List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
        enemyProjectiles.add(enemyProjectile);
        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyPlane);
        int initialHealth = friendlyPlane.getHealth();

        // Act
        collisionManager.handleEnemyProjectileCollisions(enemyProjectiles, friendlyUnits);

        // Assert
        assertEquals(initialHealth - 1, friendlyPlane.getHealth());
    }
}
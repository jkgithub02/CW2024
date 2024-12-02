//package com.example.demo.managers;
//
//import com.example.demo.actors.ActiveActorDestructible;
//import com.example.demo.actors.DestructionType;
//import com.example.demo.actors.planes.FighterPlane;
//import javafx.application.Platform;
//import javafx.geometry.BoundingBox;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//class CollisionManagerTest extends ApplicationTest {
//
//    private final CollisionManager collisionManager = new CollisionManager();
//
//    class FighterPlaneSubclass extends FighterPlane {
//        public FighterPlaneSubclass() {
//            super("imageName", 100, 0, 0, 10);
//        }
//
//        @Override
//        public ActiveActorDestructible fireProjectile() {
//            return null; // Implement as needed for your tests
//        }
//
//        @Override
//        public void updatePosition() {
//            // Implement as needed for your tests
//        }
//
//        @Override
//        public void updateActor() {
//            // Implement as needed for your tests
//        }
//    }
//
//    class ActiveActorDestructibleSubclass extends ActiveActorDestructible {
//        public ActiveActorDestructibleSubclass() {
//            super("imageName", 100, 0, 0);
//        }
//
//        @Override
//        public void updatePosition() {
//            // Implement as needed for your tests
//        }
//
//        @Override
//        public void updateActor() {
//            // Implement as needed for your tests
//        }
//
//        @Override
//        public void takeDamage() {
//            // Implement as needed for your tests
//        }
//    }
//
//    @Test
//    void friendlyPlaneDestroyedOnCollisionIfHealthZero() {
//        Platform.runLater(() -> {
//            FighterPlane friendly = mock(FighterPlaneSubclass.class);
//            ActiveActorDestructible enemy = mock(ActiveActorDestructibleSubclass.class);
//            when(friendly.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(enemy.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(friendly.getHealth()).thenReturn(0);
//
//            collisionManager.handlePlaneCollisions(List.of(friendly), List.of(enemy));
//
//            verify(friendly).destroy(DestructionType.COLLISION);
//        });
//    }
//
//    @Test
//    void enemyPlaneDestroyedOnProjectileHitIfHealthZero() {
//        Platform.runLater(() -> {
//            ActiveActorDestructible projectile = mock(ActiveActorDestructibleSubclass.class);
//            FighterPlane enemy = mock(FighterPlaneSubclass.class);
//            when(projectile.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(enemy.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(enemy.getHealth()).thenReturn(0);
//
//            collisionManager.handleUserProjectileCollisions(List.of(projectile), List.of(enemy));
//
//            verify(enemy).destroy(DestructionType.PROJECTILE_KILL);
//        });
//    }
//
//    @Test
//    void friendlyPlaneTakesDamageOnCollision() {
//        Platform.runLater(() -> {
//            FighterPlane friendly = mock(FighterPlaneSubclass.class);
//            ActiveActorDestructible enemy = mock(ActiveActorDestructibleSubclass.class);
//            when(friendly.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(enemy.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//
//            collisionManager.handlePlaneCollisions(List.of(friendly), List.of(enemy));
//
//            verify(friendly).takeDamage();
//        });
//    }
//
//    @Test
//    void enemyPlaneTakesDamageOnProjectileHit() {
//        Platform.runLater(() -> {
//            ActiveActorDestructible projectile = mock(ActiveActorDestructibleSubclass.class);
//            FighterPlane enemy = mock(FighterPlaneSubclass.class);
//            when(projectile.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//            when(enemy.getBoundsInParent()).thenReturn(new BoundingBox(0, 0, 10, 10));
//
//            collisionManager.handleUserProjectileCollisions(List.of(projectile), List.of(enemy));
//
//            verify(enemy).takeDamage();
//        });
//    }
//}
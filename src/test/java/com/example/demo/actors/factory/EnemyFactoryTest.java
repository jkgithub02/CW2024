package com.example.demo.actors.factory;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.EnemyFactory;
import com.example.demo.actors.planes.Boss;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnemyFactoryTest extends JavaFXTest {

    @Test
    void testCreateEnemy() {
        EnemyFactory factory = new EnemyFactory(EnemyFactory.EnemyType.ENEMYPLANEONE);
        ActiveActorDestructible enemy = factory.createActor(100, 200);
        assertNotNull(enemy);
    }

    @Test
    void testCreateBoss() {
        EnemyFactory bossFactory = new EnemyFactory(EnemyFactory.EnemyType.BOSS);
        Boss boss = bossFactory.createBoss();
        assertNotNull(boss);

        EnemyFactory nonBossFactory = new EnemyFactory(EnemyFactory.EnemyType.ENEMYPLANEONE);
        assertThrows(IllegalStateException.class, nonBossFactory::createBoss);
    }
}
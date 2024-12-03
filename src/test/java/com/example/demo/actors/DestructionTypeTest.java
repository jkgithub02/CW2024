package com.example.demo.actors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DestructionTypeTest {

    @Test
    void testEnumValues() {
        assertEquals(DestructionType.COLLISION, DestructionType.valueOf("COLLISION"));
        assertEquals(DestructionType.PROJECTILE_KILL, DestructionType.valueOf("PROJECTILE_KILL"));
        assertEquals(DestructionType.PENETRATED_DEFENSE, DestructionType.valueOf("PENETRATED_DEFENSE"));
    }
}
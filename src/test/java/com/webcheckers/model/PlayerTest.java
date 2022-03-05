package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void testNull(){
        Player player = new Player("test");
        assertNotNull(player);
    }
    @Test
    public void testGet(){
        Player player = new Player("test");
        assertEquals("test",player.getName());
    }
    @Test
    public void testEqualsFalse(){
        Player one = new Player("one");
        Player two = new Player("two");
        boolean result = one.equals(two);
        assertFalse(result);
        boolean next = one.equals("two");
        assertFalse(next);
    }
    @Test
    public void testToString(){
        Player player = new Player("test");
        String toString = "test";
        assertEquals(toString,player.toString());
    }
    @Test
    public void testEqualsTrue(){
        Player one = new Player("one");
        Player two = new Player("one");
        boolean result = one.equals(two);
        assertTrue(result);
        boolean next = one.equals(one);
        assertTrue(next);
    }

}
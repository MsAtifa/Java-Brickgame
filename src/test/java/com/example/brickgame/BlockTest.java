package com.example.brickgame;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    private Block testBlock;

    @BeforeEach
    public void setUp() {
        // Create a new Block instance before each test
        testBlock = new Block(1, 2, Color.CORAL, Block.BLOCK_NORMAL);
    }

    @Test
    public void testInitialization() {
        // Verify that the block is initialized correctly
        assertEquals(1, testBlock.row);
        assertEquals(2, testBlock.column);
        assertEquals(Color.CORAL, testBlock.rect.getFill());
        assertEquals(Block.BLOCK_NORMAL, testBlock.type);
        assertFalse(testBlock.isDestroyed);
    }

    @Test
    public void testHitBottom() {
        // Test checkHitToBlock for hitting the bottom
        int hitCode = testBlock.checkHitToBlock(testBlock.x + (double)getPrivateField("width", testBlock) / 2, testBlock.y + getPrivateField("height", testBlock));
        assertEquals(Block.HIT_BOTTOM, hitCode);
    }

    @Test
    public void testHitTop() {
        // Test checkHitToBlock for hitting the top
        int hitCode = testBlock.checkHitToBlock(testBlock.x + (double)getPrivateField("width", testBlock) / 2, testBlock.y);
        assertEquals(Block.HIT_TOP, hitCode);
    }

    @Test
    public void testHitRight() {
        // Test checkHitToBlock for hitting the right side
        int hitCode = testBlock.checkHitToBlock(testBlock.x + getPrivateField("width", testBlock), testBlock.y + (double)getPrivateField("height", testBlock) / 2);
        assertEquals(Block.HIT_RIGHT, hitCode);
    }

    @Test
    public void testHitLeft() {
        // Test checkHitToBlock for hitting the left side
        int hitCode = testBlock.checkHitToBlock(testBlock.x, testBlock.y + (double)getPrivateField("height", testBlock) / 2);
        assertEquals(Block.HIT_LEFT, hitCode);
    }

    @Test
    public void testNoHit() {
        // Test checkHitToBlock when there is no hit
        int hitCode = testBlock.checkHitToBlock(testBlock.x - 1, testBlock.y - 1);
        assertEquals(Block.NO_HIT, hitCode);
    }

    @Test
    public void testDraw() {
        // Verify that the draw method sets up the rectangle correctly
        assertNotNull(testBlock.rect);
        assertEquals(testBlock.x, testBlock.rect.getX(), 0.01);
        assertEquals(testBlock.y, testBlock.rect.getY(), 0.01);
        assertEquals(getPrivateField("width", testBlock), testBlock.rect.getWidth(), 0.01);
        assertEquals(getPrivateField("height", testBlock), testBlock.rect.getHeight(), 0.01);
    }

    private int getPrivateField(String fieldName, Object object) {
        try {
            Field field = Block.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (int) field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing private field: " + fieldName, e);
        }
    }
}

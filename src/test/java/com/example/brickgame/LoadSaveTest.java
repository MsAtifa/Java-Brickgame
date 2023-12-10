package com.example.brickgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoadSaveTest {

    private LoadSave loadSave;

    @BeforeEach
    public void setUp() {
        // Create a new LoadSave instance before each test
        loadSave = new LoadSave();
    }

    @Test
    public void testInitialization() {
        // Verify that the LoadSave instance is initialized correctly
        assertNotNull(loadSave);
        assertFalse(loadSave.isExistHeartBlock);
        assertFalse(loadSave.isGoldStatus);
        assertFalse(loadSave.goDownBall);
        assertFalse(loadSave.goRightBall);
        assertFalse(loadSave.collideToBreak);
        assertFalse(loadSave.collideToBreakAndMoveToRight);
        assertFalse(loadSave.collideToRightWall);
        assertFalse(loadSave.collideToLeftWall);
        assertFalse(loadSave.collideToRightBlock);
        assertFalse(loadSave.collideToBottomBlock);
        assertFalse(loadSave.collideToLeftBlock);
        assertFalse(loadSave.collideToTopBlock);
        assertEquals(0, loadSave.level);
        assertEquals(0, loadSave.score);
        assertEquals(0, loadSave.heart);
        assertEquals(0, loadSave.destroyedBlockCount);
        assertEquals(0.0, loadSave.xBall);
        assertEquals(0.0, loadSave.yBall);
        assertEquals(0.0, loadSave.xBreak);
        assertEquals(0.0, loadSave.yBreak);
        assertEquals(0.0, loadSave.centerBreakX);
        assertEquals(0.0, loadSave.centerBreakY);
        assertEquals(0L, loadSave.time);
        assertEquals(0L, loadSave.goldTime);
        assertEquals(0.0, loadSave.vX);
        assertNotNull(loadSave.blocks);
        assertTrue(loadSave.blocks.isEmpty());
    }
}

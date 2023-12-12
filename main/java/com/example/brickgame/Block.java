package com.example.brickgame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
/**
 * The Block class represents a block in the game. Blocks can have different types, such as normal, chocolate,
 * star, or heart. Each block has a position, color, and status indicating whether it is destroyed.
 * This class provides methods to draw blocks, check for collisions with the ball, and retrieve block dimensions.
 * Blocks are serializable to support saving and loading game state.
 *
 * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Block.java">Block.java Link</a>
 */
public class Block implements Serializable {
    private static final Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    /** Row position of the block in the game grid. */
    public int row;

    /** Column position of the block in the game grid. */
    public int column;

    /** Flag indicating whether the block is destroyed. */
    public boolean isDestroyed = false;

    /** Color of the block. */
    private final Color color;

    /** Type of the block, e.g., normal, chocolate, star, or heart. */
    public int type;

    /** x-coordinate of the block. */
    public int x;

    /** y-coordinate of the block. */
    public int y;

    /** Width of the block. */
    private final int width = 100;

    /** Height of the block. */
    private final int height = 30;

    /** Top padding for positioning the blocks. */
    private final int paddingTop = height * 2;

    /** Horizontal padding for positioning the blocks. */
    private final int paddingH = 50;

    /** The rectangle representing the block. */
    public Rectangle rect;

    /** Constant indicating no hit. */
    public static int NO_HIT = -1;

    /** Constant indicating a hit on the right side of the block. */
    public static int HIT_RIGHT = 0;

    /** Constant indicating a hit on the bottom side of the block. */
    public static int HIT_BOTTOM = 1;

    /** Constant indicating a hit on the left side of the block. */
    public static int HIT_LEFT = 2;

    /** Constant indicating a hit on the top side of the block. */
    public static int HIT_TOP = 3;

    /** Constant indicating a normal block type. */
    public static int BLOCK_NORMAL = 99;

    /** Constant indicating a chocolate block type. */
    public static int BLOCK_CHOCO = 100;

    /** Constant indicating a star block type. */
    public static int BLOCK_STAR = 101;

    /** Constant indicating a heart block type. */
    public static int BLOCK_HEART = 102;

    /**
     * Constructs a Block with the specified row, column, color, and type.
     *
     * @param row    The row position of the block.
     * @param column The column position of the block.
     * @param color  The color of the block.
     * @param type   The type of the block.
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    /**
     * Draws the block by setting its position, dimensions, and fill color based on its type.
     */
    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }
    }

    /**
     * Checks if the ball collides with the block and returns the type of collision.
     *
     * @param xBall The x-coordinate of the ball.
     * @param yBall The y-coordinate of the ball.
     * @return The type of collision, or NO_HIT if there is no collision.
     */
    public int checkHitToBlock(double xBall, double yBall) {
        // Check if the block is already destroyed
        if (isDestroyed) {
            return NO_HIT;
        }

        // Check if the ball is at the bottom side of the block
        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        // Check if the ball is at the top side of the block
        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        // Check if the ball is on the right side of the block
        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        // Check if the ball is on the left side of the block
        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        // If no collision is detected, return NO_HIT
        return NO_HIT;
    }

    /**
     * Gets the top padding for positioning the blocks.
     *
     * @return The top padding value.
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * Gets the horizontal padding for positioning the blocks.
     *
     * @return The horizontal padding value.
     */
    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * Gets the height of the block.
     *
     * @return The height of the block.
     */
    public static int getHeight() {
        return block.height;
    }

    /**
     * Gets the width of the block.
     *
     * @return The width of the block.
     */
    public static int getWidth() {
        return block.width;
    }
}

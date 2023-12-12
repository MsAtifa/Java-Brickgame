package com.example.brickgame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
/**
 * The {@code Bonus} class represents a bonus in the game. Bonuses are serializable
 * to support saving and loading game state. Each bonus has a position, creation time,
 * and a flag indicating whether it has been taken.
 * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Bonus.java">Bonus.java Link</a>
 */
public class Bonus implements Serializable {

    /** The rectangle representing the bonus. */
    public Rectangle choco;

    /** The x-coordinate of the bonus. */
    public double x;

    /** The y-coordinate of the bonus. */
    public double y;

    /** The time when the bonus was created. */
    public long timeCreated;

    /** Flag indicating whether the bonus has been taken. */
    public boolean taken = false;

    /**
     * Constructs a {@code Bonus} with the specified row and column positions.
     *
     * @param row    The row position of the bonus.
     * @param column The column position of the bonus.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + ((double) Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + ((double) Block.getHeight() / 2) - 15;
        draw();
    }

    /**
     * Draws the bonus by creating a rectangle with a specific image pattern.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(50);
        choco.setHeight(50);
        choco.setX(x);
        choco.setY(y);

        // Use a specific image for the bonus
        String url = "circlebonus.png";
        choco.setFill(new ImagePattern(new Image(url)));
    }
}

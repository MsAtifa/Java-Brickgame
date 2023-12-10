package com.example.brickgame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Bonus implements Serializable {
    public Rectangle choco;
    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + ((double) Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + ((double) Block.getHeight() / 2) - 15;
        draw();
    }

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

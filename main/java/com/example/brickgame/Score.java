package com.example.brickgame;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * The {@code Score} class handles the display of score-related information in the game,
 * such as updating the score label, showing messages, and displaying game-over or victory messages.
 * It utilizes JavaFX components for rendering and animations.
 * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Score.java">Score.java Link</a>
 */
public class Score {
    /**
     * Animates the fading of a label.
     *
     * @param label The label to be animated.
     */
    private void animateLabel(final Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    /**
     * Shows a score label at the specified coordinates, with a given score value.
     * The label is added to the game's root node and then animated for a fading effect.
     *
     * @param x     The x-coordinate for the label.
     * @param y     The y-coordinate for the label.
     * @param score The score value to be displayed.
     * @param main  The main game instance.
     */
    public void show(final double x, final double y, int score, final Main main) {
        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setFont(new Font(100)); // Adjust font size if necessary
        label.setStyle("-fx-text-fill: black"); // Set font color to black

        Platform.runLater(() -> main.root.getChildren().add(label));

        animateLabel(label);
    }

    /**
     * Shows a message label at a predefined position.
     * The label is added to the game's root node, and after a brief delay, it is animated for a fading effect.
     *
     * @param message The message to be displayed.
     * @param main    The main game instance.
     */
    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(200);
        label.setTranslateY(350);
        label.setFont(new Font(150)); // Adjust font size if necessary
        label.setStyle("-fx-text-fill: black"); // Set font color to black

        Platform.runLater(() -> main.root.getChildren().add(label));

        new Thread(() -> {
            try {
                Thread.sleep(1000); // Adjust duration as needed
                animateLabel(label);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Shows a game-over message along with a restart button.
     * Both the message and button are added to the game's root node.
     *
     * @param main The main game instance.
     */
    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);
            label.setStyle("-fx-text-fill: black"); // Set font color to black

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction((ActionEvent event) -> main.restartGame());
            main.root.getChildren().addAll(label, restart);
        });
    }

    /**
     * Shows a victory message along with a play-again button.
     * Both the message and button are added to the game's root node.
     *
     * @param main The main game instance.
     */
    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setFont(new Font(50)); // Adjust font size if necessary
            label.setScaleX(2);
            label.setScaleY(2);
            label.setStyle("-fx-text-fill: black"); // Set font color to black

            Button playAgain = new Button("Play Again");
            playAgain.setTranslateX(200);
            playAgain.setTranslateY(300);
            playAgain.setOnAction((ActionEvent event) -> main.restartGame());

            main.root.getChildren().addAll(label, playAgain);
        });
    }
}

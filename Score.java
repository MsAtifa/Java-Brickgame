package brickGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Score {
    private void animateLabel(final Label label) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(label.scaleXProperty(), 1), new KeyValue(label.scaleYProperty(), 1),
                        new KeyValue(label.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(1), new KeyValue(label.scaleXProperty(), 2), new KeyValue(label.scaleYProperty(), 2),
                        new KeyValue(label.opacityProperty(), 0))
        );
        timeline.play();
    }

    public void show(final double x, final double y, int score, final Main main) {
        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setFont(new Font(20)); // Adjust font size if necessary

        Platform.runLater(() -> main.root.getChildren().add(label));

        animateLabel(label);
    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(150);
        label.setTranslateY(250);
        label.setFont(new Font(20)); // Adjust font size if necessary

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

    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction((ActionEvent event) -> main.restartGame());
            main.root.getChildren().addAll(label, restart);
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setFont(new Font(20)); // Adjust font size if necessary
            label.setScaleX(2);
            label.setScaleY(2);

            main.root.getChildren().addAll(label);
        });
    }
}

package com.example.brickgame;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * The {@code PauseMenu} class provides a static method to display a pause menu using JavaFX's {@link Alert} dialog.
 * The pause menu allows the user to choose between resume, restart, or cancel options, and the choice is handled
 * using a {@link Consumer<String>} callback.
 */
public class PauseMenu {

    /**
     * Displays a pause menu with options to resume, restart, or cancel the game.
     *
     * @param choiceHandler The callback to handle the user's choice.
     *                      It accepts a string representing the chosen option ("Resume" or "Restart").
     */
    public static void showPauseMenu(Consumer<String> choiceHandler) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pause Menu");
        alert.setHeaderText(null);
        alert.setContentText("Choose an option:");

        alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

        ButtonType resumeButton = new ButtonType("Resume");
        ButtonType restartButton = new ButtonType("Restart");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(resumeButton, restartButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == resumeButton) {
                choiceHandler.accept("Resume");
            } else if (result.get() == restartButton) {
                choiceHandler.accept("Restart");
            } else {
            }
        }
    }
}

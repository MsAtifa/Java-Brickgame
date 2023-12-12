package com.example.brickgame;

import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The {@code GameEngine} class represents a simple game engine that manages the game loop, updates, and physics calculations.
 * It utilizes JavaFX's {@link Platform} to safely execute operations on the JavaFX Application Thread.
 * The engine supports setting the frames per second (FPS), starting, stopping, and pausing the game loop.
 * It provides interfaces for initializing, updating, performing physics calculations, and tracking game time.
 * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/GameEngine.java">GameEngine.java Link</a>
 */
public class GameEngine {

    private OnAction onAction;
    private int fps = 30;
    public boolean isStopped = true;
    private boolean isPaused = false;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private long time = 0;

    /**
     * Sets the {@link OnAction} listener to handle various game actions such as update, initialization,
     * physics update, and time tracking.
     *
     * @param onAction The listener to handle game actions.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * Sets the frames per second (FPS) for the game loop.
     *
     * @param fps The desired frames per second.
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    /**
     * Initializes the game engine by invoking the {@link OnAction#onInit()} method on the JavaFX Application Thread.
     */
    private void initialize() {
        Platform.runLater(onAction::onInit);
    }

    /**
     * Updates the game by scheduling the {@link OnAction#onUpdate()} method at a fixed rate.
     * The update is executed on the JavaFX Application Thread if the game is not paused.
     */
    private void update() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!isPaused) {
                Platform.runLater(onAction::onUpdate);
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    /**
     * Performs physics calculations in the game by scheduling the {@link OnAction#onPhysicsUpdate()} method at a fixed rate.
     * The calculations are executed on the JavaFX Application Thread if the game is not paused.
     * The time is also tracked and passed to the {@link OnAction#onTime(long)} method.
     */
    private void physicsCalculation() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!isPaused) {
                Platform.runLater(() -> {
                    onAction.onPhysicsUpdate();
                    onAction.onTime(time++);
                });
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts the game engine by initializing, updating, and performing physics calculations in separate threads.
     */
    public void start() {
        initialize();
        update();
        physicsCalculation();
        isStopped = false;
    }

    /**
     * Stops the game engine, shutting down the scheduler and waiting for a graceful termination.
     * If the termination takes too long, it forcefully shuts down the scheduler.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Toggles the pause state of the game engine.
     * Pausing the engine halts the update and physics calculation threads.
     */
    public void togglePause() {
        isPaused = !isPaused;
    }

    /**
     * The {@code OnAction} interface provides methods to handle various game actions,
     * including update, initialization, physics update, and time tracking.
     */
    public interface OnAction {
        /**
         * Invoked during each game loop iteration to handle general updates.
         */
        void onUpdate();

        /**
         * Invoked during game initialization to perform setup tasks.
         */
        void onInit();

        /**
         * Invoked during each game loop iteration to handle physics calculations.
         */
        void onPhysicsUpdate();

        /**
         * Invoked to track the elapsed time in the game.
         *
         * @param time The elapsed time in game loops.
         */
        void onTime(long time);
    }
}

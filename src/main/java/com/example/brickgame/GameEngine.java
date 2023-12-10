package com.example.brickgame;

import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private OnAction onAction;
    private int fps = 60;
    public boolean isStopped = true;
    private boolean isPaused = false;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private long time = 0;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and convert it to milliseconds
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    private void update() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!isPaused) {  // Check if not paused before updating
                Platform.runLater(onAction::onUpdate);
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    private void initialize() {
        Platform.runLater(onAction::onInit);
    }

    private void physicsCalculation() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!isPaused) {  // Check if not paused before running physics calculations
                Platform.runLater(() -> {
                    onAction.onPhysicsUpdate();
                    onAction.onTime(time++);
                });
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }


    public void start() {
        initialize();
        update();
        physicsCalculation();
        isStopped = false;
    }


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

    public void togglePause() {
        isPaused = !isPaused;
    }

    public interface OnAction {
        void onUpdate();
        void onInit();
        void onPhysicsUpdate();
        void onTime(long time);
    }
}

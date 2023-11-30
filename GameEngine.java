package brickGame;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    public boolean isStopped = true;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private long time = 0;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    private void update() {
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(onAction::onUpdate), 0, fps, TimeUnit.MILLISECONDS);
    }

    private void initialize() {
        Platform.runLater(onAction::onInit);
    }

    private void physicsCalculation() {
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(onAction::onPhysicsUpdate);
            onAction.onTime(time++);
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    

    public void start() {
        Initialize();
        Update();
        PhysicsCalculation();
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

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }

}

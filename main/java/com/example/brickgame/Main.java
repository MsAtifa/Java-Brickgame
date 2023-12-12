package com.example.brickgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Collections;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * The {@code Main} class represents the main entry point of the game application. It extends
 * {@code Application} and implements {@code EventHandler<KeyEvent>} and {@code GameEngine.OnAction}.
 * The game involves breaking blocks and progressing through different levels.
 * <p>
 * The class manages the game's graphical interface, user input handling, and game state.
 * It includes methods for initializing the game components, handling key events, and managing
 * the overall game flow.
 * </p>
 * <p>
 * The game window displays essential information such as the player's score, remaining hearts,
 * and the current level. The player interacts with the game using the arrow keys to control
 * the paddle/break and navigate through menus.
 * </p>
 * <p>
 * The game utilizes a {@code GameEngine} for handling game logic, physics, and time-based events.
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     launch(args);
 * }
 * }
 * </pre>
 * </p>
 *
 * @see Application
 * @see EventHandler
 * @see GameEngine
 ** Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">Main.java Link</a>
 */
public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    /** Represents the current level of the game. */
    private int level = 0;
    /**The x-coordinate of the left edge of the paddle/break.*/
    private double xBreak = 0.0f;

    /**The x-coordinate of the center of the paddle/break.*/
    private double centerBreakX;
    /**The y-coordinate of the top edge of the paddle/break.*/
    private double yBreak = 640.0f;
    /**The width of the paddle/break.*/
    private final int breakWidth = 130;
    /**The height of the paddle/break.*/
    private final int breakHeight = 30;
    /**Half of the paddle/break width.*/
    private final int halfBreakWidth = breakWidth / 2;
    /**The width of the game scene.*/
    private final int sceneWidth = 500;
    /**The height of the game scene.*/
    private final int sceneHeight = 700;
    /**Constant representing the left direction for movement.*/
    private static final int LEFT = 1;
    /**Constant representing the right direction for movement.*/
    private static final int RIGHT = 2;
    /**Represents the game ball*/
    private Circle ball;
    /**The x-coordinate of the center of the ball.*/
    private double xBall;
    /**The y-coordinate of the center of the ball.*/
    private double yBall;
    /**Indicates whether the ball is in gold status.*/
    private boolean isGoldStatus = false;
    /**Indicates whether a heart block exists in the game.*/
    private boolean isExistHeartBlock = false;
    /**Represents the paddle/break as a rectangle.*/
    private Rectangle rect;
    /**The radius of the game ball.*/
    private final int ballRadius = 15;
    /**The count of destroyed blocks.*/
    private int destroyedBlockCount = 0;
    /**Represents the player's remaining lives (hearts).*/
    private int heart = 3;
    /**Represents the player's score.*/
    private int score = 0;
    /**The current time in the game.*/
    private long time = 0;
    /**The time when the ball hits an object.*/
    private long hitTime = 0;
    /**The time when the ball enters gold status.*/
    private long goldTime = 0;
    /**The game engine responsible for game updates.*/
    private GameEngine engine;
    /**The file path for saving game state.*/
    public static String savePath = "D:/save/save.mdds";
    /**The directory path for saving game state.*/
    public static String savePathDir = "D:/save/";
    /**List of blocks in the game.*/
    private final ArrayList<Block> blocks = new ArrayList<>();
    /**List of bonus (choco) objects in the game.*/
    private final ArrayList<Bonus> chocos = new ArrayList<>();
    /**Array of colors used for block representation.*/
    private final Color[] colors = new Color[]{
            Color.rgb(190, 190, 190),
            Color.rgb(152, 251, 152),
            Color.rgb(240, 190, 240),
            Color.VIOLET,
            Color.rgb(64, 224, 208),
            Color.rgb(192, 0, 128),
            Color.CORAL,
            Color.rgb(200, 162, 200)
    };
    /**The root {@code Pane} for the game scene.*/
    public Pane root;
    /**The label displaying the player's score.*/
    private Label scoreLabel;
    /**The label displaying the remaining hearts.*/
    private Label heartLabel;
    /**The label displaying the current level.*/
    private Label levelLabel;

    /**Indicates whether the game is loaded from a saved state.*/
    private boolean loadFromSave;

    Stage primaryStage;
    Button load = null;
    Button newGame = null;
    /**
     * The main entry point of the application. Invoked by the JavaFX runtime.
     *
     * @param primaryStage The primary stage for displaying the game window.
     */

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        if (!loadFromSave) {
            level++;
            if (level > 1 && level < 9) {
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 9) {
                new Score().showWin(this);
                return;
            }

            initBall();
            initBreak();
            initBoard();

            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            newGame.setId("newGame");
            load.setTranslateX(220);
            load.setTranslateY(300);
            newGame.setTranslateX(195);
            newGame.setTranslateY(350);

        }

        root = new Pane();
        Image backgroundImage = new Image("kittybg.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Set the background on the root Pane
        root.setBackground(new Background(background));
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label(":" + heart);
        ImageView heartImageView = new ImageView(new Image("chocoheart.png"));
        heartImageView.setFitWidth(25); // Adjust the width as needed
        heartImageView.setFitHeight(25); // Adjust the height as needed
        heartLabel.setGraphic(heartImageView);
        heartLabel.setTranslateX(sceneWidth - 60);
        if (!loadFromSave) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, newGame);
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (!loadFromSave) {
            if (level > 1 && level < 9) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            load.setOnAction(event -> {
                loadGame();
                load.setVisible(false);
                newGame.setVisible(false);
            });

            newGame.setOnAction(event -> {
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
                load.setVisible(false);
                newGame.setVisible(false);
            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }
    }

    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(10); // Random number between 0 and 9

                int type;
                if (r == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r == 2) {
                    type = Block.BLOCK_HEART;
                } else if (r == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }

                blocks.add(new Block(j, i, colors[new Random().nextInt(colors.length)], type));
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Handles key events, such as arrow key presses, to control the game.
     *
     * @param event The key event triggered by the user.
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case DOWN:
                break;
            case S:
                saveGame();
                break;
            case P:
                // Pressed 'P' key, toggle pause
                engine.togglePause();
                PauseMenu.showPauseMenu(choice -> handlePauseMenuChoice(choice));
                break;

        }
    }

    private void handlePauseMenuChoice(String choice) {
        // Handle the user's choice from the pause menu
        switch (choice) {
            case "Resume":
                // Resume the game
                engine.togglePause();
                break;
            case "Restart":
                // Restart the game
                restartGame();
                break;
        }
    }

    /**
     * Moves the paddle/break in the specified direction using animation.
     *
     * @param direction The direction of the movement (LEFT or RIGHT).
     */

    public void move(final int direction) {
        double animationDuration = 500.0; // milliseconds
        int frames = 30;

        KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration / frames), event -> {
            if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                return;
            }
            if (xBreak == 0 && direction == LEFT) {
                return;
            }
            if (direction == RIGHT) {
                xBreak++;
            } else {
                xBreak--;
            }
            centerBreakX = xBreak + halfBreakWidth;
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(Collections.nCopies(frames, keyFrame));
        timeline.setCycleCount(1);

        timeline.setOnFinished(event -> {
        });

        timeline.play();
    }

    /**
     * Initializes the game ball with random starting position.
     */
    private void initBall() {
        Random random = new Random();

        // Calculate a starting position between the paddle and the blocks
        int startYRange = ((level + 1) * Block.getHeight()) + 15;
        int endYRange = sceneHeight - 200;
        int startY = startYRange + random.nextInt(endYRange - startYRange);

        // Set the initial coordinates of the ball
        xBall = sceneWidth / 2.0;  // Start in the middle of the scene horizontally
        yBall = startY;

        // Create and configure the ball
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setStroke(Color.BLACK);
        ball.setStrokeWidth(2);
        ball.setFill(new ImagePattern(new Image("kuromiball.png")));
    }


    /**
     * Initializes the game paddle/break.
     */

    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("newpaddle.png"));

        rect.setFill(pattern);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2.0);
    }


    private boolean goDownBall = true;
    private boolean goRightBall = true;
    private boolean collideToBreak = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall = false;
    private boolean collideToLeftWall = false;
    private boolean collideToRightBlock = false;
    private boolean collideToBottomBlock = false;
    private boolean collideToLeftBlock = false;
    private boolean collideToTopBlock = false;

    private double vX = 1.5;
    private double vY = 1.000;

    private void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    /**
     * Handles game physics, including ball movement, collisions, and updates.
     */

    private void setPhysicsToBall() {
        updateBallPosition();

        if (handleTopAndBottomBoundaries()) return;
        if (handleBallCollisionWithBreak()) return;
        if (handleBallCollisionWithWalls()) return;

        handleBallCollisionWithBlocks();
    }

    private void updateBallPosition() {
        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }
    }


    private boolean handleTopAndBottomBoundaries() {
        if (yBall <= 0) {
            resetCollideFlags();
            goDownBall = true;
            return true;
        }

        if (yBall >= sceneHeight) {
            goDownBall = false;
            if (!isGoldStatus) {
                handleGameOver();
                return true;
            }

        }
        return false;
    }


    private void handleGameOver() {
        heart--;
        new Score().show((double) sceneWidth / 2, (double) sceneHeight / 2, -1, this);

        if (heart == 0) {
            new Score().showGameOver(this);
            engine.stop();
        }
    }

    private boolean handleBallCollisionWithBreak() {
        if (yBall >= yBreak - ballRadius && xBall >= xBreak && xBall <= xBreak + breakWidth) {
            handleBreakCollision();
            return true;
        }

        return false;
    }

    private void handleBreakCollision() {
        hitTime = time;
        resetCollideFlags();
        collideToBreak = true;
        goDownBall = false;

        double relation = (xBall - centerBreakX) / (double) (breakWidth / 2);

        if (Math.abs(relation) <= 0.3) {
            vX = Math.abs(relation);
        } else if (Math.abs(relation) <= 0.7) {
            vX = (Math.abs(relation) * 1.5) + (level / 3.500);
        } else {
            vX = (Math.abs(relation) * 2) + (level / 3.500);
        }

        collideToBreakAndMoveToRight = (xBall - centerBreakX > 0);
    }

    private boolean handleBallCollisionWithWalls() {
        if (xBall >= sceneWidth - ballRadius) {
            resetCollideFlags();
            collideToRightWall = true;
            goRightBall = false;
            return true;
        }

        if (xBall <= 0 + ballRadius) {
            resetCollideFlags();
            collideToLeftWall = true;
            goRightBall = true;
            return true;
        }

        return false;
    }

    private void handleBallCollisionWithBlocks() {
        if (collideToRightBlock || collideToLeftBlock) {
            goRightBall = true;
        }

        if (collideToTopBlock) {
            goDownBall = true;
        }

        if (collideToBottomBlock) {
            goDownBall = true;
        }
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            nextLevel();
        }
    }

    /**
     * Saves the current game state to a file in a separate thread.
     */
    private void saveGame() {
        new Thread(() -> {
            File saveDir = new File(savePathDir);
            if (saveDir.mkdirs()) {
                File file = new File(savePath);
                try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                    saveGameState(outputStream);
                    new Score().showMessage("Game Saved", Main.this);
                } catch (IOException e) {
                    // Replace 'printStackTrace()' with more robust logging
                    logError("Error saving game:", e);
                }
            } else {
                logError("Failed to create directories for saving the game.");
            }
        }).start();
    }

    private void logError(String message, Exception e) {
        // Replace with your logging framework or write to a log file
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, message, e);
    }

    private void logError(String message) {
        logError(message, null);
    }


    private void saveGameState(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(level);
        outputStream.writeInt(score);
        outputStream.writeInt(heart);
        outputStream.writeInt(destroyedBlockCount);

        outputStream.writeDouble(xBall);
        outputStream.writeDouble(yBall);
        outputStream.writeDouble(xBreak);
        outputStream.writeDouble(yBreak);
        outputStream.writeDouble(centerBreakX);
        outputStream.writeLong(time);
        outputStream.writeLong(goldTime);
        outputStream.writeDouble(vX);

        outputStream.writeBoolean(isExistHeartBlock);
        outputStream.writeBoolean(isGoldStatus);
        outputStream.writeBoolean(goDownBall);
        outputStream.writeBoolean(goRightBall);
        outputStream.writeBoolean(collideToBreak);
        outputStream.writeBoolean(collideToBreakAndMoveToRight);
        outputStream.writeBoolean(collideToRightWall);
        outputStream.writeBoolean(collideToLeftWall);
        outputStream.writeBoolean(collideToRightBlock);
        outputStream.writeBoolean(collideToBottomBlock);
        outputStream.writeBoolean(collideToLeftBlock);
        outputStream.writeBoolean(collideToTopBlock);

        ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
        for (Block block : blocks) {
            if (!block.isDestroyed) {
                blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
            }
        }

        outputStream.writeObject(blockSerializables);
    }

    /**
     * Loads the saved game state from a file and restores the game.
     */
    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();


        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStatus;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        collideToBreak = loadSave.collideToBreak;
        collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBlock = loadSave.collideToRightBlock;
        collideToBottomBlock = loadSave.collideToBottomBlock;
        collideToLeftBlock = loadSave.collideToLeftBlock;
        collideToTopBlock = loadSave.collideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xBreak = loadSave.xBreak;
        yBreak = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j,colors[r % colors.length],ser.type));
        }

        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            logError("Error starting the game after loading:", e);
        }

    }

    /**
     * Advances the game to the next level.
     */
    private void nextLevel() {
        Platform.runLater(() -> {
            try {
                vX = 1.5;

                engine.stop();
                resetCollideFlags();
                goDownBall = true;

                isGoldStatus = false;
                isExistHeartBlock = false;

                hitTime = 0;
                time = 0;
                goldTime = 0;

                engine.stop();
                blocks.clear();
                chocos.clear();
                destroyedBlockCount = 0;
                start(primaryStage);
            } catch (Exception e) {
                logError("Error advancing to the next level:", e);
            }
        });
    }

    /**
     * Restarts the game with initial settings.
     */

    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.5;
            destroyedBlockCount = 0;
            resetCollideFlags();
            goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            logError("Error restarting the game:", e);
        }
    }

    /**
     * Runs when the game is updated by the {@code GameEngine}.
     */

    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            updateLabelsAndShapes();
        });

        handleBallBlockCollisions();
    }

    /**
     * Updates the labels and shapes in the game window.
     */

    private void updateLabelsAndShapes() {
        scoreLabel.setText("Score: " + score);
        heartLabel.setText(":" + heart);

        rect.setX(xBreak);
        rect.setY(yBreak);
        ball.setCenterX(xBall);
        ball.setCenterY(yBall);

        for (Bonus choco : chocos) {
            choco.choco.setY(choco.y);
        }
    }

    /**
     * Handles collisions between the ball and game blocks.
     */

    private void handleBallBlockCollisions() {
        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    resetCollisionFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(() -> root.getChildren().add(choco.choco));
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        isGoldStatus = true;
                        ball.setFill(new ImagePattern(new Image("keroppiball.png")));
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        ball.setRadius(ballRadius);
                        ball.setStroke(Color.BLACK); // Set the border color
                        ball.setStrokeWidth(2);
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }
                    setCollisionFlags(hitCode);
                    if (hitCode == Block.HIT_TOP) {
                        goDownBall = true; // Set goDownBall to true to continue moving downward
                    }
                }
            }
        }
    }

    private void setCollisionFlags(int hitCode) {
        if (hitCode == Block.HIT_RIGHT) {
            collideToRightBlock = true;
        } else if (hitCode == Block.HIT_BOTTOM) {
            collideToBottomBlock = true;
        } else if (hitCode == Block.HIT_LEFT) {
            collideToLeftBlock = true;
        } else if (hitCode == Block.HIT_TOP) {
            collideToTopBlock = true;
        }
    }

    private void resetCollisionFlags() {
        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    /**
     * Initializes the game and sets up components when the game starts.
     */
    @Override
    public void onInit() {
    }

    /**
     * Handles physics updates, collisions, and time-based events.
     */
    @Override
    public void onPhysicsUpdate() {
        Platform.runLater(() -> {
            checkDestroyedCount();
            setPhysicsToBall();
            GoldStatus();
            updateChocos();
        });
    }

            private void GoldStatus(){
                if (time - goldTime > 1000) {
                    ball.setFill(new ImagePattern(new Image("kuromiball.png")));
                    root.getStyleClass().remove("goldRoot");
                    isGoldStatus = false;
                }
            }

            private void updateChocos(){
                double speedMultiplier = 1.0;
                for (Bonus choco : chocos) {
                    if (choco.y > sceneHeight || choco.taken) {
                        continue;
                    }
                    if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.taken = true;
                        choco.choco.setVisible(false);
                        score += 3;
                        new Score().show(choco.x, choco.y, 3, this);
                    } else {
                        choco.y += speedMultiplier;
                    }
                }

            }

    /**
     * Called on every time update, providing the current time.
     *
     * @param time The current time in milliseconds.
     */

    @Override
    public void onTime(long time) {
        this.time = time;
    }
}

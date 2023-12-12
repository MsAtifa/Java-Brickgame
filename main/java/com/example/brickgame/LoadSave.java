package com.example.brickgame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The {@code LoadSave} class is responsible for loading saved game data from a file.
 * It contains fields representing various game states and properties, as well as a method to read
 * and populate these fields from a saved game file. The saved game data includes information about
 * the current level, score, remaining hearts, destroyed block count, ball and paddle positions, time-related
 * variables, and various flags indicating game state.
 * <p>
 * The class utilizes {@code ObjectInputStream} to deserialize the saved game file and extract
 * the stored information. In case of exceptions during the reading process, appropriate warnings
 * are logged.
 * </p>
 * <p>
 * The class also includes a field for storing an {@code ArrayList} of {@code BlockSerializable} objects,
 * which represent the state of blocks in the game. This list is deserialized from the saved file.
 * </p>
 *
 * @see BlockSerializable
 * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/LoadSave.java">LoadSave.java Link</a>
 */
public class LoadSave {
    public boolean          isExistHeartBlock;
    public boolean          isGoldStatus;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          collideToBreak;
    public boolean          collideToBreakAndMoveToRight;
    public boolean          collideToRightWall;
    public boolean          collideToLeftWall;
    public boolean          collideToRightBlock;
    public boolean          collideToBottomBlock;
    public boolean          collideToLeftBlock;
    public boolean          collideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public double           centerBreakY;
    public long             time;
    public long             goldTime;
    public double           vX;
    public ArrayList<BlockSerializable> blocks = new ArrayList<>();

    /**
     * Reads and populates the game state from a saved game file.
     * Uses {@code ObjectInputStream} to deserialize the file and extract the stored information.
     * Logs warnings in case of exceptions during the reading process.
     */
    public void read() {

        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        try
                (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.savePath)))
        {

            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();


            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            centerBreakY = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();


            isExistHeartBlock = inputStream.readBoolean();
            isGoldStatus = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToBreak = inputStream.readBoolean();
            collideToBreakAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBlock = inputStream.readBoolean();
            collideToBottomBlock = inputStream.readBoolean();
            collideToLeftBlock = inputStream.readBoolean();
            collideToTopBlock = inputStream.readBoolean();


            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                LOGGER.warning("Exception while reading blocks: " + e.getMessage());
            }
        } catch (IOException e) {
            LOGGER.warning("Error reading saved game: " + e.getMessage());
        }
    }
}

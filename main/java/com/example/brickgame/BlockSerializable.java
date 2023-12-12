package com.example.brickgame;

import java.io.Serializable;

/**
 * The {@code BlockSerializable} class represents a serializable version of a block in a game.
 * It is used for storing and transmitting block information in a serialized format.
 * This class includes the row, column, and type of the block as final fields,
 * making it suitable for serializing block data without modification.
 */
public class BlockSerializable implements Serializable {

    /** The row position of the block. */
    public final int row;

    /** The column position of the block. */
    public final int j;

    /** The type of the block. */
    public final int type;

    /**
     * Constructs a {@code BlockSerializable} with the specified row, column, and type.
     *
     * @param row  The row position of the block.
     * @param j    The column position of the block.
     * @param type The type of the block.
     * Original source: <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/BlockSerializable.java">BlockSerializable.java Link</a>
     */
    public BlockSerializable(int row, int j, int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}

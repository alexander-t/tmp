package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.mario.entity.BlockBase;

import java.util.List;

public class Level {

    private List<BlockBase> blocks;
    private final int widthInBlocks;
    private final int heightInBlocks;

    public Level(int widthInBlocks, int heightInBlocks, List<BlockBase> blocks) {
        this.blocks = blocks;
        this.widthInBlocks = widthInBlocks;
        this.heightInBlocks = heightInBlocks;
    }

    public List<BlockBase> getBlocks() {
        return blocks;
    }

    public int getWidthInBlocks() {
        return widthInBlocks;
    }

    public int getHeightInBlocks() {
        return heightInBlocks;
    }

    public int widthInPixels() {
        return widthInBlocks * BlockBase.BLOCK_SIZE;
    }

    public int heightInPixels() {
        return heightInBlocks * BlockBase.BLOCK_SIZE;
    }
}

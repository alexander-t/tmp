package se.tarlinder.platformer;

import se.tarlinder.platformer.mario.Level;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.mario.entity.ImmobileBlock;

import java.util.ArrayList;
import java.util.List;

public class StringLevelBuilder {

    public static Level buildLevel(String[] levelData) {
        List<BlockBase> blocks = new ArrayList<>();
        int height = levelData.length;
        int width = levelData[0].length();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char tileSymbol = levelData[y].charAt(x);
                if (tileSymbol != ' ') {
                    blocks.add(new ImmobileBlock(x * BlockBase.BLOCK_SIZE,
                            y *  BlockBase.BLOCK_SIZE, tileSymbol + ""));
                }
            }
        }
        return new Level(width, height, blocks);
    }
}

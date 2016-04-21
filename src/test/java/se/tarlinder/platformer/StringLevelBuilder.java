package se.tarlinder.platformer;

import se.tarnowski.platformer.mario.Level;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.BumpableBlock;
import se.tarnowski.platformer.mario.entity.ImmobileBlock;

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
                if (tileSymbol == 'I') {
                    blocks.add(new ImmobileBlock(x * 24, y * 24, tileSymbol + ""));
                }
            }
        }
        return new Level(width, height, blocks);
    }
}

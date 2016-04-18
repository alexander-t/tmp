package se.tarnowski.platformer.mario;

import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.BumpableBlock;
import se.tarnowski.platformer.mario.entity.ImmobileBlock;

import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {
    private static String[] level = {
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "         IIIIIII    BBB                   ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                    BBBBBBBB              ",
            "                                          ",
            "                               BBIB       ",
            "                                          ",
            "I                                        I",
            "I   BB                        IIIII      I",
            "I                   I    I              II",
            "I  BBBBB           II    II              I",
            "I                 III    III             I",
            "IIIIIIIIIIIIIIIIIIIII    IIIIIIIIIIIIIIIII"
    };

    public static List<BlockBase> buildLevel() {
        List<BlockBase> blocks = new ArrayList<>();
        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length(); x++) {
                char blockType = level[y].charAt(x);
                if (blockType == 'I') {
                    blocks.add(new ImmobileBlock(x * BlockBase.BLOCK_SIZE, y * BlockBase.BLOCK_SIZE, "block immobile"));
                } else if (blockType == 'B') {
                    blocks.add(new BumpableBlock(x * BlockBase.BLOCK_SIZE, y * BlockBase.BLOCK_SIZE, "block bumpable"));
                }
            }
        }
        return blocks;
    }
}

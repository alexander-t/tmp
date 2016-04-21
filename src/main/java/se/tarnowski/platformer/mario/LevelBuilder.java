package se.tarnowski.platformer.mario;

import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.BumpableBlock;
import se.tarnowski.platformer.mario.entity.ImmobileBlock;
import se.tarnowski.platformer.tmx.SimpleTmxLoader;
import se.tarnowski.platformer.tmx.TmxTile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {

    public static Level buildLevel() {
        List<BlockBase> blocks = new ArrayList<>();
        SimpleTmxLoader tmxLoader = new SimpleTmxLoader(new InputStreamReader(LevelBuilder.class.getResourceAsStream("/levels/mario.tmx")));
        tmxLoader.getTiles().values().stream().forEach(t ->
                SpriteCache.addImage("" + t.getId(), t.getImage().replaceAll("^.*(sprites\\/.*)", "/$1")));
        for (int y = 0; y < tmxLoader.getMapHeight(); y++) {
            for (int x = 0; x < tmxLoader.getMapWidth(); x++) {
                int tileGid = tmxLoader.getLevel()[y][x];
                if (tileGid > 0) {
                    TmxTile tile = tmxLoader.getTiles().get(tileGid);
                    String tileType = tile.getProperties().get("type");
                    if ("immobile".equals(tileType)) {
                        blocks.add(new ImmobileBlock(x * 24 - 1, y * 24 - 1, tile.getId() + ""));
                    } else {
                        blocks.add(new BumpableBlock(x * 24 - 1, y * 24 - 1, tile.getId() + ""));
                    }
                }
            }
        }
        return new Level(tmxLoader.getMapWidth(), tmxLoader.getMapHeight(), blocks);
    }
}

package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.mario.entity.BumpableBlock;
import se.tarlinder.platformer.mario.entity.ImmobileBlock;
import se.tarlinder.platformer.tmx.SimpleTmxLoader;
import se.tarlinder.platformer.tmx.TmxTile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {


    private SimpleTmxLoader tmxLoader;

    public LevelBuilder(SimpleTmxLoader tmxLoader) {
        this.tmxLoader = tmxLoader;
    }

    public Level buildLevel() {
        List<BlockBase> blocks = new ArrayList<>();
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

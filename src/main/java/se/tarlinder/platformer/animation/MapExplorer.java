package se.tarlinder.platformer.animation;

import se.tarlinder.platformer.mario.entity.BumpableBlock;
import se.tarlinder.platformer.animation.view.ScrollingViewPort;
import se.tarlinder.platformer.engine.input.swing.KeyAdapterInputHandler;
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.mario.entity.ImmobileBlock;
import se.tarlinder.platformer.tmx.SimpleTmxLoader;
import se.tarlinder.platformer.tmx.TmxTile;

import javax.swing.*;
import java.io.InputStreamReader;

public class MapExplorer extends JFrame {

    public static final int MS_PER_FRAME = 16;

    private final KeyAdapterInputHandler inputHandler;
    private ScrollPlayer player = new ScrollPlayer();
    private final ScrollingViewPort viewPort;

    public MapExplorer() {

        SimpleTmxLoader tmxLoader = new SimpleTmxLoader(new InputStreamReader(getClass().getResourceAsStream("/levels/mario.tmx")));
        tmxLoader.getTiles().values().stream().forEach(t ->
                SpriteCache.addImage("" + t.getId(), t.getImage().replaceAll("^.*(sprites\\/.*)", "/$1")));

        SpriteCache.addImage("player", "/sprites/crosshair.png");

        BlockBase[][] level = new BlockBase[tmxLoader.getMapHeight()][tmxLoader.getMapWidth()];
        for (int y = 0; y < tmxLoader.getMapHeight(); y++) {
            for (int x = 0; x < tmxLoader.getMapWidth(); x++) {
                int tileGid = tmxLoader.getLevel()[y][x];
                if (tileGid > 0) {
                    TmxTile tile = tmxLoader.getTiles().get(tileGid);
                    String tileType = tile.getProperties().get("type");
                    if ("immobile".equals(tileType)) {
                        level[y][x] = new ImmobileBlock(x * 24 - 1, y * 24 - 1, tile.getId() + "");
                    } else {
                        level[y][x] = new BumpableBlock(x * 24 - 1, y * 24 - 1, tile.getId() + "");
                    }
                }
            }
        }


        inputHandler = new KeyAdapterInputHandler();
        viewPort = new ScrollingViewPort(player, level);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(inputHandler);
        add(viewPort);
        pack();
        setVisible(true);

        new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();

                processInput();
                update();

                viewPort.repaint();

                try {
                    Thread.sleep(Math.max(startTime + MS_PER_FRAME - System.currentTimeMillis(), 0));
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    private void processInput() {

        if (inputHandler.rightArrowPressed) {
            player.moveRight();
        }

        if (inputHandler.leftArrowPressed) {
            player.moveLeft();
        }

        if (inputHandler.downArrowPressed) {
            player.moveDown();
        }

        if (inputHandler.upArrowPressed) {
            player.moveUp();
        }
    }

    private void update() {
        player.update();
    }

    public static void main(String... args) {
        new MapExplorer();
    }
}

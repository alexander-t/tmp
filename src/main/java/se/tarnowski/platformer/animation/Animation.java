package se.tarnowski.platformer.animation;

import se.tarnowski.platformer.animation.view.ScrollingViewPort;
import se.tarnowski.platformer.engine.input.swing.KeyAdapterInputHandler;
import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.BumpableBlock;
import se.tarnowski.platformer.mario.entity.ImmobileBlock;
import se.tarnowski.platformer.tmx.SimpleTmxLoader;
import se.tarnowski.platformer.tmx.TmxTile;

import javax.swing.*;
import java.io.File;

public class Animation extends JFrame {

    public static final int MS_PER_FRAME = 16;

    private final KeyAdapterInputHandler inputHandler;
    private ScrollPlayer player = new ScrollPlayer();
    private final ScrollingViewPort viewPort;

    public Animation() {

        SimpleTmxLoader tmxLoader = new SimpleTmxLoader(new File("/Users/alexander/Desktop/mario.tmx"));
        tmxLoader.getTiles().stream().forEach(t ->
                SpriteCache.addImage(t.getId(), t.getImage().replaceAll("^.*resources(.*)", "$1")));

        SpriteCache.addSheet("enemies", "/smb_enemies_sheet.png");
        SpriteCache.add("player", "enemies", 0, 4, 16, 16);

        inputHandler = new KeyAdapterInputHandler();

        BlockBase[][] level = new BlockBase[tmxLoader.getMapHeight()][tmxLoader.getMapWidth()];
        for (int y = 0; y < tmxLoader.getMapHeight(); y++) {
            for (int x = 0; x < tmxLoader.getMapWidth(); x++) {
                int id = tmxLoader.getLevel()[y][x];
                if (id == 0) {
                    level[y][x] = null;
                } else if (id == 1 || id == 3) {
                    level[y][x] = new ImmobileBlock(x * 24 - 1, y * 24 - 1, id + "");
                } else if (id == 2 || id == 4) {
                    level[y][x] = new BumpableBlock(x * 24 - 1, y * 24 - 1, id + "");
                } else {
                    level[y][x] = new ImmobileBlock(x * 24 - 1, y * 24 - 1, id + "");
                }
            }
        }


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
        new Animation();
    }
}

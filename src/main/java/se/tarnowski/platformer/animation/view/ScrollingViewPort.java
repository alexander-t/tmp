package se.tarnowski.platformer.animation.view;

import se.tarnowski.platformer.animation.ScrollPlayer;
import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.ImmobileBlock;
import se.tarnowski.platformer.mario.view.swing.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class ScrollingViewPort extends JPanel {

    private static final int WIDTH = 384;
    private static final int HEIGHT = 768 / 2;

    private ScrollPlayer player;
    private BlockBase[][] level;

    private Image backgroundImage;
    private int camX = 0;
    private int camY = 0;

    public ScrollingViewPort(ScrollPlayer player, BlockBase[][] level) {
        this.player = player;
        this.level = level;
        player.setViewPort(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        backgroundImage = ImageUtils.loadImageResource("/background.png");
    }


    public int getWidth() {
        return WIDTH;
    }

    @Override
    public void paint(Graphics g) {
        g.translate(-camX, -camY);
        g.drawImage(backgroundImage, 0, 0, null);


        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level.length; x++) {
                BlockBase block = level[y][x];
                if (block != null) {
                    g.drawImage(SpriteCache.get(block.getCurrentImageId()), block.getX(), block.getY(), null);
                }
            }
        }

        g.setColor(Color.white);
        g.drawImage(SpriteCache.get("player"), player.getX(), player.getY(), null);
        //g.drawString("x=" + player.getX() + ", worldx=" + worldX, 20, 20);
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }
}

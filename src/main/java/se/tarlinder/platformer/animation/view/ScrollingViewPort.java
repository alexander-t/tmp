package se.tarlinder.platformer.animation.view;

import se.tarlinder.platformer.animation.ScrollPlayer;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;
import se.tarlinder.platformer.mario.view.swing.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class ScrollingViewPort extends JPanel {

    private static final int WIDTH = 24 * 20 - 2;
    private static final int HEIGHT = 24 * 15 - 1;

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
        setDoubleBuffered(true);
    }


    public int getWidth() {
        return WIDTH;
    }

    @Override
    public void paint(Graphics g) {
        g.translate(-camX, -camY);
        g.setColor(Color.blue);
        g.fillRect(camX, camY, getWidth(), getHeight());
        //g.drawImage(backgroundImage, 0, 0, null);


        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level.length; x++) {
                BlockBase block = level[y][x];
                if (block != null && isInViewPort(block)) {
                    g.drawImage(SpriteCache.get(block.getCurrentImageId()), (int) block.getX(), (int) block.getY(), null);
                }
            }

        }

        g.drawImage(SpriteCache.get("player"), (int) player.getX() - 24, (int) player.getY() - 24, null);
        g.setColor(Color.white);
        g.drawString(String.format("x=%d, y=%d    camx=%d, camy=%d", player.getX(), player.getY(), camX, camY), camX + 1, camY + 10);
    }

    private boolean isInViewPort(BlockBase block) {

        return block.getX() + BlockBase.BLOCK_SIZE >= camX && block.getX() <= camX + getWidth()
                && block.getY() + BlockBase.BLOCK_SIZE >= camY && block.getY() <= camY + getHeight();
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }
}

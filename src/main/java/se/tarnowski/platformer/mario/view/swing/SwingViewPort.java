package se.tarnowski.platformer.mario.view.swing;

import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.engine.view.ViewPort;
import se.tarnowski.platformer.mario.GameContext;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.Player;

import javax.swing.*;
import java.awt.*;

public class SwingViewPort extends JFrame implements ViewPort {

    private int camX = 0;
    private int camY = 0;
    private JPanel viewPort;
    private Image backgroundImage;

    public SwingViewPort(GameContext gameContext) {
        gameContext.setViewPort(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        viewPort = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.translate(-camX, -camY);
                g.drawImage(backgroundImage, camX / 5, camY / 5, null);

                for (BlockBase block : gameContext.getLevel().getBlocks()) {
                    g.drawImage(SpriteCache.get(block.getCurrentImageId()), block.getX(), block.getY(), null);
                }

                for (Entity enemy : gameContext.enemies) {
                    g.drawImage(SpriteCache.get(enemy.getCurrentImageId()), enemy.getX(), enemy.getY(), null);
                }

                Player player = gameContext.player;
                g.drawImage(SpriteCache.get(player.getCurrentImageId()), player.getX(), player.getY(), null);

                g.setColor(Color.lightGray);
                g.fill3DRect(camX, camY, getWidth(), 16, true);
                g.setColor(Color.black);
                g.drawString(String.format("x=%d, y=%d", player.getX(), player.getY()), camX + 4, camY + 12);
            }
        };
        viewPort.setPreferredSize(new Dimension(BlockBase.BLOCK_SIZE * 32, BlockBase.BLOCK_SIZE * 22));
        add(viewPort);
        pack();
        setVisible(true);
        backgroundImage = ImageUtils.loadImageResource("/sprites/realistic/sky.jpg");
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }

    public void resetCamera() {
        camX = camY = 0;
    }
}

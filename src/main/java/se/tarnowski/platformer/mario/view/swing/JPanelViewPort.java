package se.tarnowski.platformer.mario.view.swing;

import se.tarnowski.platformer.mario.GameContext;
import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.mario.entity.Player;

import javax.swing.*;
import java.awt.*;

public class JPanelViewPort extends JPanel {

    private Image backgroundImage;
    private GameContext gameContext;

    public JPanelViewPort(GameContext gameContext) {
        this.gameContext = gameContext;
        setPreferredSize(new Dimension(1008, 768));
        backgroundImage = ImageUtils.loadImageResource("/background.png");
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);

        for (BlockBase block : gameContext.blocks) {
            g.drawImage(SpriteCache.get(block.getCurrentImageId()), block.getX(), block.getY(), null);
        }

        for (Entity enemy : gameContext.enemies) {
            g.drawImage(SpriteCache.get(enemy.getCurrentImageId()), enemy.getX(), enemy.getY(), null);
        }

        Player player = gameContext.player;
        g.drawImage(SpriteCache.get(player.getCurrentImageId()), player.getX(), player.getY(), null);

        g.setColor(Color.white);
        g.drawString("y=" + player.getY(), 20, 20);
    }
}

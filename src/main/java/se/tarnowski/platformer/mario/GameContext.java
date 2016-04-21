package se.tarnowski.platformer.mario;

import se.tarnowski.platformer.engine.VerticalDirection;
import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.engine.view.ViewPort;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.Goomba;
import se.tarnowski.platformer.mario.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameContext {

    private ViewPort viewPort;
    public Player player;
    private Level level;
    public List<Entity> enemies = new ArrayList<>();

    public GameContext(Player player, Level level) {
        this.player = player;
        this.level = level;
        player.setGameContext(this);
    }

    public boolean detectCollisionsWithWorld(Rectangle rectangle, VerticalDirection verticalDirection) {
        for (BlockBase block : level.getBlocks()) {
            if (rectangle.intersects(block.getBoundingRectangle())) {
                if (verticalDirection == VerticalDirection.UP) {
                    block.bump();
                }
                return true;
            }
        }
        return false;
    }

    public boolean detectCollisionsWithEnemies(Rectangle rectangle) {
        for (Entity enemy : enemies) {
            if (rectangle.intersects(enemy.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }

    public void addEnemy(Goomba goomba) {
        enemies.add(goomba);
    }

    public void update() {
        level.getBlocks().stream().forEach(b -> b.update());
        enemies.stream().forEach(e -> e.update());
        player.update();
    }

    public int getViewPortWidth() {
        return viewPort.getWidth();
    }

    public int getViewPortHeight() {
        return viewPort.getHeight();
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

    public void setViewPort(ViewPort viewPort) {
        this.viewPort = viewPort;
    }

    public Level getLevel() {
        return level;
    }
}

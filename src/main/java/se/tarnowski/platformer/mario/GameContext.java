package se.tarnowski.platformer.mario;

import se.tarnowski.platformer.engine.VerticalDirection;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.mario.entity.Goomba;
import se.tarnowski.platformer.mario.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameContext {

    public Player player;
    public List<BlockBase> blocks;
    public List<Entity> enemies = new ArrayList<>();

    public GameContext(Player player, List<BlockBase> blocks) {
        this.player = player;
        this.blocks = blocks;
        player.setGameContext(this);
    }

    public boolean detectCollisionsWithWorld(Rectangle rectangle, VerticalDirection verticalDirection) {
        for (BlockBase block : blocks) {
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
        for (BlockBase block : blocks) {
            block.update();
        }
        for (Entity enemy : enemies) {
            enemy.update();
        }
        player.update();
    }

    public int getScreenHeight() {
        return 768;
    }
}

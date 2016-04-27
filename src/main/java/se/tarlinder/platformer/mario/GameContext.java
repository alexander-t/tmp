package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.entity.Entity;
import se.tarlinder.platformer.engine.view.ViewPort;
import se.tarlinder.platformer.mario.entity.Goomba;
import se.tarlinder.platformer.mario.entity.Player;

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

/*
    public boolean detectCollisionsWithEnemies(Rectangle rectangle) {
        for (Entity enemy : enemies) {
            if (rectangle.intersects(enemy.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }
*/
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

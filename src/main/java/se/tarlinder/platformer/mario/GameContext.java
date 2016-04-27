package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.entity.Entity;
import se.tarlinder.platformer.engine.view.ViewPort;

import java.util.ArrayList;
import java.util.List;

public class GameContext {

    private ViewPort viewPort;
    private Level level;
    public List<Entity> movingEntities = new ArrayList<>();

    public GameContext(Level level) {
        this.level = level;
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
    public void addEntity(Entity entity) {
        movingEntities.add(entity);
    }

    public void update() {
        level.getBlocks().stream().forEach(b -> b.update());
        movingEntities.stream().forEach(e -> e.update());
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

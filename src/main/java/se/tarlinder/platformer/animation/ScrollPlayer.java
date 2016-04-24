package se.tarlinder.platformer.animation;

import se.tarlinder.platformer.animation.view.ScrollingViewPort;
import se.tarlinder.platformer.engine.entity.Entity;
import se.tarlinder.platformer.mario.entity.BlockBase;

public class ScrollPlayer extends Entity {

    private ScrollingViewPort viewPort;
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    public ScrollPlayer() {
        super(10 * BlockBase.BLOCK_SIZE, 3 * BlockBase.BLOCK_SIZE);
    }

    @Override
    public String getCurrentImageId() {
        return "player";
    }

    @Override
    public void update() {

        // Limit movement to level
        if (x < 0) {
            x = 0;
        } else if (x > 24 * WIDTH - viewPort.getWidth() / 2) {
            x = 24 * WIDTH - viewPort.getWidth() / 2;
        }

        if (y < 0) {
            y = 0;
        } else if (y > 16 * HEIGHT - viewPort.getHeight() / 2) {
            y = 16 * HEIGHT - viewPort.getHeight() / 2;
        }

        // Move camera
        if (x >= viewPort.getWidth() / 2) {
            viewPort.setCamX((int) x - viewPort.getWidth() / 2);
        }

        if (y >= viewPort.getHeight() / 2) {
            viewPort.setCamY((int) y - viewPort.getHeight() / 2);
        }
    }

    public void moveRight() {
        x += 4;
    }

    public void moveLeft() {
        x -= 4;
    }

    public void moveDown() {
        y += 4;
    }

    public void moveUp() {
        y -= 4;
    }

    public void setViewPort(ScrollingViewPort viewPort) {
        this.viewPort = viewPort;
    }
}

package se.tarnowski.platformer.animation;

import se.tarnowski.platformer.animation.view.ScrollingViewPort;
import se.tarnowski.platformer.engine.entity.Entity;

import java.awt.*;

public class ScrollPlayer extends Entity {

    private ScrollingViewPort viewPort;

    public ScrollPlayer() {
        super(0, 63);
    }

    @Override
    public String getCurrentImageId() {
        return "player";
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return null;
    }

    @Override
    public void update() {
        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

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

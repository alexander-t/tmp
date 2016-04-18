package se.tarnowski.platformer.engine.entity;

import java.awt.*;

public abstract class Entity {

    protected float x;
    protected float y;

    public abstract String getCurrentImageId();
    public abstract Rectangle getBoundingRectangle();
    public abstract void update();

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}

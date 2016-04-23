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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

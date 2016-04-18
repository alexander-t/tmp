package se.tarnowski.platformer.mario.entity;

import se.tarnowski.platformer.engine.entity.Entity;

import java.awt.*;

public abstract class BlockBase extends Entity {

    public static final int BLOCK_SIZE = 24;

    protected String imageId;

    public BlockBase(int x, int y, String imageId) {
        super(x, y);
        this.imageId = imageId;
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle((int) x, (int) y, BLOCK_SIZE, BLOCK_SIZE);
    }

    @Override
    public void update() {
        // Default is to not move at all
    }

    @Override
    public String getCurrentImageId() {
        return imageId;
    }

    public void bump() {
        // Default is to not get bumped
    }
}

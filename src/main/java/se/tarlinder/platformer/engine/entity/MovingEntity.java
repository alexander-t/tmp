package se.tarlinder.platformer.engine.entity;

import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;

import java.awt.*;

public abstract class MovingEntity extends Entity {
    protected HorizontalDirection horizontalDirection;
    protected VerticalDirection verticalDirection;
    protected float velocity;
    protected float verticalVelocity;

    ///////////////////////////////////////////
    protected boolean isJumping;
    public final int framesPerJump = 16;
    public int frameInJump = 0;
    /////////////////////////////////////////////

    public MovingEntity(int x, int y, float initialVelocity, HorizontalDirection initialHorizontalDirection, VerticalDirection initialVerticalDirection) {
        super(x, y);
        velocity = initialVelocity;
        horizontalDirection = initialHorizontalDirection;
        verticalDirection = initialVerticalDirection;
    }

    public abstract Point[][] getCollisionPoints();

    public HorizontalDirection getHorizontalDirection() {
        return horizontalDirection;
    }

    public void setHorizontalDirection(HorizontalDirection horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    public VerticalDirection getVerticalDirection() {
        return verticalDirection;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVerticalVelocity(float verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public float getVerticalVelocity() {
        return verticalVelocity;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void startJump() {
        if (!isJumping && verticalVelocity == 0) {
            isJumping = true;
            frameInJump = 0;
        }
    }

    public void endJump() {
        isJumping = false;
    }
}

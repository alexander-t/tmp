package se.tarnowski.platformer.engine.entity;

import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.engine.VerticalDirection;

public abstract class MovingEntity extends Entity {
    protected HorizontalDirection horizontalDirection;
    protected VerticalDirection verticalDirection;
    protected float velocity;
    protected float verticalVelocity;

    public MovingEntity(int x, int y, float initialVelocity, HorizontalDirection initialHorizontalDirection, VerticalDirection initialVerticalDirection) {
        super(x, y);
        velocity = initialVelocity;
        horizontalDirection = initialHorizontalDirection;
        verticalDirection = initialVerticalDirection;
    }

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
}

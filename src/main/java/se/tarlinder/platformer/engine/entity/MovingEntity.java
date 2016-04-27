package se.tarlinder.platformer.engine.entity;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;
import se.tarlinder.platformer.mario.state.LifeState;

import java.awt.*;

public abstract class MovingEntity extends Entity {
    protected HorizontalDirection horizontalDirection;
    protected VerticalDirection verticalDirection;
    protected float velocity;
    protected float verticalVelocity;

    protected Animation walkRightAnimation;
    protected Animation walkLeftAnimation;
    protected Animation currentAnimation;
    protected String jumpLeftImageId;
    protected String jumpRightimageId;

    protected LifeState lifeState = LifeState.ALIVE;

    ///////////////////////////////////////////
    protected boolean isJumping;
    public final int framesPerJump = 18;
    public int frameInJump = 0;
    /////////////////////////////////////////////

    public MovingEntity(int x, int y, int width, int height,
                        float initialVelocity, HorizontalDirection initialHorizontalDirection, VerticalDirection initialVerticalDirection,
                        Animation walkRightAnimation, Animation walkLeftAnimation) {
        super(x, y, width, height);
        velocity = initialVelocity;
        horizontalDirection = initialHorizontalDirection;
        verticalDirection = initialVerticalDirection;
        this.walkRightAnimation = walkRightAnimation;
        this.walkLeftAnimation = walkLeftAnimation;
        currentAnimation = horizontalDirection == HorizontalDirection.RIGHT
                ? walkRightAnimation : walkLeftAnimation;

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

    public Animation getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation getAnimation() {
        return currentAnimation;
    }

    public void setAnimation(Animation animation) {
        currentAnimation = animation;
    }

    public String getJumpLeftImageId() {
        return jumpLeftImageId;
    }

    public String getJumpRightimageId() {
        return jumpRightimageId;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void startJump() {
        if (!isJumping) {
            isJumping = true;
            frameInJump = 0;
        }
    }

    public void endJump() {
        isJumping = false;
    }

    public void kill() {
        lifeState = LifeState.DEAD;
    }

    public LifeState getLifeState() {
        return lifeState;
    }
}

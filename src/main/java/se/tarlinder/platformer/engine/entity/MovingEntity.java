package se.tarlinder.platformer.engine.entity;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.Collision;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;
import se.tarlinder.platformer.engine.component.ai.AIComponent;
import se.tarlinder.platformer.engine.component.camera.CameraComponent;
import se.tarlinder.platformer.engine.component.input.InputComponent;
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

    protected InputComponent inputComponent;
    protected AIComponent aiComponent;
    protected CameraComponent cameraComponent;

    protected Collision collision = Collision.NONE;

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

    public MovingEntity withInputComponent(InputComponent inputComponent) {
        this.inputComponent = inputComponent;
        return this;
    }

    public MovingEntity withCameraComponent(CameraComponent cameraComponent) {
        this.cameraComponent = cameraComponent;
        return this;
    }

    public MovingEntity withAIComponent(AIComponent aiComponent) {
        this.aiComponent = aiComponent;
        return this;
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

    public void setVerticalDirection(VerticalDirection verticalDirection) {
        this.verticalDirection = verticalDirection;
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

    public String getJumpRightImageId() {
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

    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    public Collision getCollision() {
        return collision;
    }
}

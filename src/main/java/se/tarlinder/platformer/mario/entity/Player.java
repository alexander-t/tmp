package se.tarlinder.platformer.mario.entity;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.component.GraphicsComponent;
import se.tarlinder.platformer.engine.component.PhysicsComponent;
import se.tarlinder.platformer.engine.component.camera.CameraComponent;
import se.tarlinder.platformer.engine.component.camera.ScrollViewPortCameraComponent;
import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;
import se.tarlinder.platformer.mario.state.LifeState;

import java.awt.*;

public class Player extends MovingEntity {

    private InputComponent inputComponent;
    private PhysicsComponent physicsComponent = new PhysicsComponent();
    private GraphicsComponent graphicsComponent = new GraphicsComponent();
    private CameraComponent cameraComponent = new ScrollViewPortCameraComponent();

    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 24;

    private static Point[][] COLLISION_POINTS = new Point[][]{
            {new Point(3, 2), new Point(SPRITE_WIDTH - 3, 2)},
            {new Point(SPRITE_WIDTH, 4), new Point(SPRITE_WIDTH, SPRITE_HEIGHT - 4)},
            {new Point(3, SPRITE_HEIGHT - 2), new Point(SPRITE_WIDTH - 3, SPRITE_HEIGHT - 2)},
            {new Point(0, 4), new Point(0, SPRITE_HEIGHT - 4)},
    };

    private GameContext gameContext;

    private static final String IMAGE_ID_DYING = "mario dying";
    private static final String IMAGE_ID_JUMP_LEFT = "mario jump left";
    private static final String IMAGE_ID_JUMP_RIGHT = "mario jump right";

    private String currentImageId;

    public Player(int x, int y, InputComponent inputComponent) {
        super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT, 0, HorizontalDirection.RIGHT, null,
                new Animation()
                        .add("mario walk right1", 3)
                        .add("mario walk right2", 5)
                        .add("mario walk right3", 3)
                        .add("mario walk right4", 5),
                new Animation()
                        .add("mario walk left1", 3)
                        .add("mario walk left2", 5)
                        .add("mario walk left3", 3)
                        .add("mario walk left4", 5));

        this.inputComponent = inputComponent;
        this.jumpRightimageId = IMAGE_ID_JUMP_RIGHT;
        this.jumpLeftImageId = IMAGE_ID_JUMP_LEFT;
    }

    public Player withCameraComponent(CameraComponent cameraComponent) {
        this.cameraComponent = cameraComponent;
        return this;
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public String getCurrentImageId() {
        return currentImageId;
    }

    public Point[][] getCollisionPoints() {
        Point[][] absolutePoints = new Point[COLLISION_POINTS.length][COLLISION_POINTS[0].length];
        for (int y = 0; y < COLLISION_POINTS.length; y++) {
            for (int x = 0; x < COLLISION_POINTS[0].length; x++) {
                absolutePoints[y][x] = new Point(COLLISION_POINTS[y][x].x + (int) getX(), COLLISION_POINTS[y][x].y + (int) getY());
            }
        }
        return absolutePoints;
    }

    @Override
    public void update() {
        float oldVelocity = velocity;

        inputComponent.update(this);
        physicsComponent.update(this, gameContext.getLevel());

        boolean hasChangedDirection = (oldVelocity * velocity < 0);
        currentImageId = graphicsComponent.update(this, hasChangedDirection);
        cameraComponent.update(this, gameContext);
/*
        if (lifeState == lifeState.DYING) {
            currentImageId = IMAGE_ID_DYING;
            if (animationTicks++ < 5) {
                y -= 3;
            } else {
                if (y < viewPortHeight) {
                    y += 1.5;
                } else {
                    lifeState = lifeState.DEAD;
                }
            }
            return;
        }

        if (verticalDirection != VerticalDirection.UP) {
            // Is player on solid ground?
            if (gameContext.detectCollisionsWithWorld(getBoundingRectangleYExpandedBy(4), VerticalDirection.DOWN)) {
                verticalDirection = VerticalDirection.NONE;
            } else {
                verticalDirection = VerticalDirection.DOWN;
                y += 4;
                if (y >= gameContext.getLevel().heightInPixels()) {
                    lifeState = LifeState.DEAD;
                    return;
                }
            }
        }

        if (verticalDirection == VerticalDirection.DOWN) {
            currentImageId = horizontalDirection == HorizontalDirection.RIGHT ? IMAGE_ID_JUMP_RIGHT : IMAGE_ID_JUMP_LEFT;
        } else if (verticalDirection == VerticalDirection.UP) {
            if (animationTicks++ < FRAMES_PER_JUMP) {
                Rectangle rectangle = getBoundingRectangleYExpandedBy(-4);
                if (!gameContext.detectCollisionsWithWorld(rectangle, VerticalDirection.UP)) {
                    y -= 4;
                } else {
                    animationTicks = FRAMES_PER_JUMP;
                }
            } else {
                // Start falling
                verticalDirection = VerticalDirection.DOWN;
            }
            currentImageId = horizontalDirection == HorizontalDirection.RIGHT ?
                    IMAGE_ID_JUMP_RIGHT : IMAGE_ID_JUMP_LEFT;
        } else {

            // Reset to standing still if player hasn't moved
            if (oldX == x) {
                frameIndex = 0;
            }
            currentImageId = horizontalDirection == HorizontalDirection.RIGHT ?
                    imagesFacingRight[frameIndex] : imagesFacingLeft[frameIndex];
        }
        oldX = x;

        if (gameContext.detectCollisionsWithEnemies(getBoundingRectangle())) {
            lifeState = lifeState.DYING;
            animationTicks = 0;
        }
        */
    }

    public void respawnAt(float x, float y) {
        this.x = x;
        this.y = y;
        lifeState = LifeState.ALIVE;
        gameContext.getViewPort().resetCamera();
    }
}



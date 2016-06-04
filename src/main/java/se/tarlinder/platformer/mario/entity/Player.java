package se.tarlinder.platformer.mario.entity;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.component.DeathComponent;
import se.tarlinder.platformer.engine.component.GraphicsComponent;
import se.tarlinder.platformer.engine.component.PhysicsComponent;
import se.tarlinder.platformer.engine.component.ai.NullAIComponent;
import se.tarlinder.platformer.engine.component.camera.ScrollViewPortCameraComponent;
import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;
import se.tarlinder.platformer.mario.state.LifeState;

import java.awt.*;

public class Player extends MovingEntity {

    private PhysicsComponent physicsComponent = new PhysicsComponent();
    private GraphicsComponent graphicsComponent = new GraphicsComponent();
    private DeathComponent deathComponent = new DeathComponent();

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

    public Player(int x, int y, GameContext gameContext, InputComponent inputComponent) {
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

        this.gameContext = gameContext;
        this.inputComponent = inputComponent;

        aiComponent = new NullAIComponent();
        cameraComponent = new ScrollViewPortCameraComponent();
        jumpRightimageId = IMAGE_ID_JUMP_RIGHT;
        jumpLeftImageId = IMAGE_ID_JUMP_LEFT;
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
        aiComponent.update(this);
        physicsComponent.update(this, gameContext.getLevel());
        deathComponent.update(this);

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



package se.tarnowski.platformer.mario.entity;

import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.engine.VerticalDirection;
import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.mario.GameContext;
import se.tarnowski.platformer.mario.state.LifeState;

import java.awt.*;

public class Player extends Entity {
    private GameContext gameContext;

    private String[] imagesFacingRight;
    private String[] imagesFacingLeft;
    private String currentImageId;
    private int frameIndex;

    private float oldX;
    private int dx = 2;
    private LifeState lifeState = LifeState.ALIVE;

    private int animationTicks;
    private VerticalDirection verticalDirection = VerticalDirection.NONE;
    private HorizontalDirection horizontalDirection = HorizontalDirection.RIGHT;

    private static final int FRAMES_PER_JUMP = 18;
    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 24;

    private static final String IMAGE_ID_DYING = "mario dying";
    private static final String IMAGE_ID_JUMP_LEFT = "mario jump left";
    private static final String IMAGE_ID_JUMP_RIGHT = "mario jump right";

    public Player(int x, int y) {
        super(x, y);
        oldX = x;

        imagesFacingRight = new String[]{
                "mario walk right1",
                "mario walk right1",
                "mario walk right2",
                "mario walk right2",
                "mario walk right2",
                "mario walk right2",
                "mario walk right3",
                "mario walk right3",
                "mario walk right4",
                "mario walk right4",
                "mario walk right4",
                "mario walk right4",
        };

        imagesFacingLeft = new String[]{
                "mario walk left1",
                "mario walk left1",
                "mario walk left2",
                "mario walk left2",
                "mario walk left2",
                "mario walk left2",
                "mario walk left3",
                "mario walk left3",
                "mario walk left4",
                "mario walk left4",
                "mario walk left4",
                "mario walk left4",
        };

        currentImageId = imagesFacingRight[0];
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public String getCurrentImageId() {
        return currentImageId;
    }

    public void moveRight() {
        if (lifeState == LifeState.DYING) {
            return;
        }

        horizontalDirection = HorizontalDirection.RIGHT;
        if (!gameContext.detectCollisionsWithWorld(getBoundingRectangleXExpandedBy(dx), VerticalDirection.NONE)) {
            x += dx;
        }
        frameIndex++;
        frameIndex %= imagesFacingRight.length;
    }

    public void moveLeft() {
        if (lifeState == LifeState.DYING) {
            return;
        }

        horizontalDirection = HorizontalDirection.LEFT;
        if (!gameContext.detectCollisionsWithWorld(getBoundingRectangleXExpandedBy(-4), VerticalDirection.NONE)) {
            x -= dx;
        }
        frameIndex++;
        frameIndex %= imagesFacingRight.length;
    }

    public void walk() {
        dx = 2;
    }

    public void sprint() {
        if (verticalDirection == VerticalDirection.NONE) {
            dx = 4;
        }
    }

    public void jump() {
        if (lifeState == LifeState.DYING) {
            return;
        }

        if (verticalDirection == VerticalDirection.NONE) {
            verticalDirection = VerticalDirection.UP;
            animationTicks = 0;
        }
    }

    public void respawnAt(float x, float y) {
        this.x = x;
        this.y = y;
        frameIndex = 0;
        lifeState = LifeState.ALIVE;
        gameContext.getViewPort().resetCamera();
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle((int) x, (int) y, SPRITE_WIDTH, SPRITE_WIDTH);
    }

    public Rectangle getBoundingRectangleYExpandedBy(int dy) {
        if (dy >= 0) {
            return new Rectangle((int) x, (int) y, SPRITE_WIDTH, SPRITE_HEIGHT - 1 + dy);
        } else {
            return new Rectangle((int) x, (int) y + dy, SPRITE_WIDTH, SPRITE_HEIGHT - 1 - dy);
        }
    }

    public Rectangle getBoundingRectangleXExpandedBy(int dx) {
        if (dx >= 0) {
            return new Rectangle((int) x, (int) y, SPRITE_WIDTH + dx, SPRITE_HEIGHT - 1);
        } else {
            return new Rectangle((int) x + dx, (int) y, SPRITE_WIDTH - dx, SPRITE_HEIGHT - 1);
        }
    }

    @Override
    public void update() {

        final int viewPortWidth = gameContext.getViewPortWidth();
        final int viewPortHeight = gameContext.getViewPortHeight();

        // Limit movement to level
        if (x < 0) {
            x = 0;
        } else if (x > gameContext.getLevel().widthInPixels() - SPRITE_WIDTH) {
            x = gameContext.getLevel().widthInPixels() - SPRITE_WIDTH;
        }

        if (y < 0) {
            y = 0;
        }

        // Move camera
        if (x >= viewPortWidth / 2) {
            if (x < gameContext.getLevel().widthInPixels() - viewPortWidth / 2) {
                gameContext.getViewPort().setCamX((int) x - gameContext.getViewPort().getWidth() / 2);
            }
        }

        if (y >= viewPortHeight / 2) {
            if (y < gameContext.getLevel().heightInPixels() - viewPortHeight / 2) {
                gameContext.getViewPort().setCamY((int) y - gameContext.getViewPort().getHeight() / 2);
            }
        }

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
    }

    public LifeState getLifeState() {
        return lifeState;
    }

    // For testing
    VerticalDirection getVerticalDirection() {
        return verticalDirection;
    }

    HorizontalDirection getHorizontalDirection() {
        return horizontalDirection;
    }
}



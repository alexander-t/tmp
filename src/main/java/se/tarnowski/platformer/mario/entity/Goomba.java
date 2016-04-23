package se.tarnowski.platformer.mario.entity;

import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.engine.VerticalDirection;
import se.tarnowski.platformer.engine.component.InputComponent;
import se.tarnowski.platformer.engine.component.PhysicsComponent;
import se.tarnowski.platformer.engine.entity.MovingEntity;
import se.tarnowski.platformer.mario.GameContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Goomba extends MovingEntity {

    public static final float WALK_ACCELERATION = 0.7f;

    private GameContext gameContext;

    private List<String> frames = new ArrayList<>();
    private String currentImageId;
    private int frameIndex;

    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    private static Point[][] COLLISION_POINTS = new Point[][]{
            {new Point(3, 0), new Point(SPRITE_WIDTH - 3, 0)},
            {new Point(SPRITE_WIDTH, 2), new Point(SPRITE_WIDTH, SPRITE_HEIGHT - 2)},
            {new Point(2, SPRITE_HEIGHT), new Point(SPRITE_WIDTH - 2, SPRITE_HEIGHT)},
            {new Point(0, 2), new Point(0, SPRITE_HEIGHT - 2)},
    };

    private InputComponent inputComponent = new InputComponent();
    private PhysicsComponent physicsComponent = new PhysicsComponent();

    public Goomba(int x, int y, GameContext gameContext) {
        super(x, y, WALK_ACCELERATION, HorizontalDirection.RIGHT, VerticalDirection.NONE);
        this.gameContext = gameContext;

        frames.add("goomba walk1");
        frames.add("goomba walk2");

        currentImageId = frames.get(0);
    }

    @Override
    public String getCurrentImageId() {
        return currentImageId;
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle((int) x, (int) y, SPRITE_WIDTH, SPRITE_HEIGHT);
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
        inputComponent.update(this);
        physicsComponent.update(this, gameContext.getLevel());

        if (frameIndex < 15) {
            currentImageId = frames.get(0);
            frameIndex++;
        } else if (frameIndex < 30) {
            currentImageId = frames.get(1);
            frameIndex++;
        } else {
            frameIndex = 0;
        }
    }
}

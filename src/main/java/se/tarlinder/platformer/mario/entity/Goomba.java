package se.tarlinder.platformer.mario.entity;

import se.tarlinder.platformer.engine.VerticalDirection;
import se.tarlinder.platformer.engine.component.PhysicsComponent;
import se.tarlinder.platformer.engine.component.input.DumbWalkerInputComponent;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.mario.GameContext;

import java.awt.*;

public class Goomba extends MovingEntity {

    public static final float WALK_ACCELERATION = 0.7f;

    private GameContext gameContext;

    private Animation animation = new Animation();

    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    private static Point[][] COLLISION_POINTS = new Point[][]{
            {new Point(3, 0), new Point(SPRITE_WIDTH - 3, 0)},
            {new Point(SPRITE_WIDTH, 2), new Point(SPRITE_WIDTH, SPRITE_HEIGHT - 2)},
            {new Point(2, SPRITE_HEIGHT), new Point(SPRITE_WIDTH - 2, SPRITE_HEIGHT)},
            {new Point(0, 2), new Point(0, SPRITE_HEIGHT - 2)},
    };

    private InputComponent inputComponent = new DumbWalkerInputComponent();
    private PhysicsComponent physicsComponent = new PhysicsComponent();

    public Goomba(int x, int y, GameContext gameContext) {
        super(x, y, WALK_ACCELERATION, HorizontalDirection.RIGHT, VerticalDirection.NONE);
        this.gameContext = gameContext;

        animation.add("goomba walk1", 15);
        animation.add("goomba walk2", 15);
    }

    @Override
    public String getCurrentImageId() {
        return animation.getCurrentImageId();
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
        animation.advance();
    }
}

package se.tarlinder.platformer.mario.entity;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;
import se.tarlinder.platformer.engine.component.GraphicsComponent;
import se.tarlinder.platformer.engine.component.PhysicsComponent;
import se.tarlinder.platformer.engine.component.camera.CameraComponent;
import se.tarlinder.platformer.engine.component.camera.NullCameraComponent;
import se.tarlinder.platformer.engine.component.input.DumbWalkerInputComponent;
import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;

import java.awt.*;

public class Goomba extends MovingEntity {

    public static final float WALK_ACCELERATION = 0.7f;

    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static Point[][] COLLISION_POINTS = new Point[][]{
            {new Point(3, 0), new Point(WIDTH - 3, 0)},
            {new Point(WIDTH, 2), new Point(WIDTH, HEIGHT - 2)},
            {new Point(2, HEIGHT), new Point(WIDTH - 2, HEIGHT)},
            {new Point(0, 2), new Point(0, HEIGHT - 2)},
    };

    private GameContext gameContext;
    private PhysicsComponent physicsComponent = new PhysicsComponent();
    private GraphicsComponent graphicsComponent = new GraphicsComponent();

    private String currentImageId;

    public Goomba(int x, int y, GameContext gameContext) {
        super(x, y, WIDTH, HEIGHT, WALK_ACCELERATION, HorizontalDirection.RIGHT, VerticalDirection.NONE,
                new Animation().add("goomba walk1", 15).add("goomba walk2", 15),
                new Animation().add("goomba walk1", 15).add("goomba walk2", 15));
        this.gameContext = gameContext;
        inputComponent = new DumbWalkerInputComponent();
        cameraComponent = new NullCameraComponent();
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
        inputComponent.update(this);
        physicsComponent.update(this, gameContext.getLevel());
        currentImageId = graphicsComponent.update(this, false);
        cameraComponent.update(this, gameContext);
    }
}

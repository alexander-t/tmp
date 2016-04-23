package se.tarnowski.platformer.engine.component;

import se.tarnowski.platformer.engine.Collision;
import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.mario.Level;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.Goomba;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhysicsComponent {

    public static final float BASE_VERTICAL_VELOCITY = 1.0f;


    public void update(Goomba goomba, Level level) {
        goomba.setX(goomba.getX() + goomba.getVelocity());
        goomba.setY(goomba.getY() + goomba.getVerticalVelocity());

        Set<Collision> collisions = detectCollisionsWithWorld(goomba, level);

        if (collisions.contains(Collision.DOWN)) {
            goomba.setY(goomba.getY() - goomba.getVerticalVelocity());
            goomba.setVerticalVelocity(0);
        } else {
            if (goomba.getVerticalVelocity() == 0) {
                goomba.setVerticalVelocity(BASE_VERTICAL_VELOCITY);
            } else {
                goomba.setVerticalVelocity(fakeAcceleration(goomba.getVerticalVelocity()));
            }
            goomba.setVelocity(0);
        }

        if (collisions.contains(Collision.RIGHT)) {
            goomba.setHorizontalDirection(HorizontalDirection.LEFT);
            goomba.setX(goomba.getX() - goomba.getVelocity());
        }
    }

    public Set<Collision> detectCollisionsWithWorld(Goomba goomba, Level level) {
        Set<Collision> collisions = new HashSet<>(4);

        // No optimization! Loop through the _entire_ level
        for (BlockBase block : level.getBlocks()) {
            if (isEitherPointInRectangle(goomba.getCollisionPoints()[Collision.RIGHT.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.RIGHT);
            } else if (isEitherPointInRectangle(goomba.getCollisionPoints()[Collision.DOWN.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.DOWN);
            }
        }
        return collisions;
    }

    private float fakeAcceleration(float verticalVelocity) {
        // Completely arbitrary formula
        return (float) Math.min(Math.pow(verticalVelocity + 0.001f, 2), 5.0);
    }

    private boolean isEitherPointInRectangle(Point[] points, Rectangle rectangle) {
        return Arrays.stream(points).anyMatch(rectangle::contains);
    }
}

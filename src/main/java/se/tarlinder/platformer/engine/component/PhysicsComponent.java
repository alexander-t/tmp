package se.tarlinder.platformer.engine.component;

import se.tarlinder.platformer.engine.Collision;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.Level;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.mario.state.LifeState;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhysicsComponent {

    public static final float BASE_VERTICAL_VELOCITY = 1.0f;

    public void update(MovingEntity entity, Level level) {

        if (entity.getLifeState() == LifeState.DEAD) {
            return;
        }

        // Resolve jump
        if (entity.isJumping()) {
            if (entity.frameInJump++ < entity.framesPerJump) {
                entity.setVerticalVelocity(-4);
            } else if (entity.frameInJump == entity.framesPerJump) {
                entity.setVerticalVelocity(0);
            }
        }

        // Move based on velocity
        entity.setX(entity.getX() + entity.getVelocity());
        entity.setY(entity.getY() + entity.getVerticalVelocity());

        if (entity.getX() < 0) {
            entity.setX(0);
        }

        // Determine facing
        if (entity.getVelocity() > 0) {
            entity.setHorizontalDirection(HorizontalDirection.RIGHT);
        } else if (entity.getVelocity() < 0) {
            entity.setHorizontalDirection(HorizontalDirection.LEFT);
        }

        Set<Collision> collisions = detectCollisionsWithWorld(entity, level);

        // Remain/land on ground or start falling
        if (collisions.contains(Collision.DOWN)) {
            entity.setY(entity.getY() - entity.getVerticalVelocity());
            entity.setVerticalVelocity(0);
            entity.endJump();
        } else if (collisions.contains(Collision.UP)) {
            entity.setY(entity.getY() - entity.getVerticalVelocity());
            entity.setVerticalVelocity(BASE_VERTICAL_VELOCITY);
            entity.endJump();
        } else {
            if (entity.getVerticalVelocity() == 0) {
                entity.setVerticalVelocity(BASE_VERTICAL_VELOCITY);
            } else {
                entity.setVerticalVelocity(fakeAcceleration(entity.getVerticalVelocity()));
            }
        }

        // Resolve collisions sidewise
        if (collisions.contains(Collision.RIGHT)) {
            entity.setHorizontalDirection(HorizontalDirection.LEFT);
            entity.setX(entity.getX() - entity.getVelocity());
        } else if (collisions.contains(Collision.LEFT)) {
            entity.setHorizontalDirection(HorizontalDirection.RIGHT);
            entity.setX(entity.getX() - entity.getVelocity());
        }

        if (entity.getY() > level.heightInPixels()) {
            entity.kill();
        }
    }

    public Set<Collision> detectCollisionsWithWorld(MovingEntity entity, Level level) {
        Set<Collision> collisions = new HashSet<>(4);

        // No optimization! Loop through the _entire_ level
        for (BlockBase block : level.getBlocks()) {
            if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.RIGHT.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.RIGHT);
            } else if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.LEFT.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.LEFT);
            }

            if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.DOWN.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.DOWN);
            } else if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.UP.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.UP);
                block.bump();
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

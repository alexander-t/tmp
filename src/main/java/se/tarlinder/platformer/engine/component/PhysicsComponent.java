package se.tarlinder.platformer.engine.component;

import se.tarlinder.platformer.engine.Collision;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;
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

        // Determine movement direction
        if (entity.getVelocity() > 0) {
            entity.setHorizontalDirection(HorizontalDirection.RIGHT);
        } else if (entity.getVelocity() < 0) {
            entity.setHorizontalDirection(HorizontalDirection.LEFT);
        }
        if (entity.getVerticalVelocity() > 0) {
            entity.setVerticalDirection(VerticalDirection.DOWN);
        } else if (entity.getVerticalVelocity() < 0) {
            entity.setVerticalDirection(VerticalDirection.UP);
        } else {
            entity.setVerticalDirection(VerticalDirection.NONE);
        }


        Set<Collision> collisions = detectHorizontalCollisionsWithWorld(entity, level);
        if (collisions.contains(Collision.RIGHT)) {
            entity.setCollision(Collision.RIGHT);
            entity.setX(entity.getX() - entity.getVelocity());
        } else if (collisions.contains(Collision.LEFT)) {
            entity.setCollision(Collision.LEFT);
            entity.setX(entity.getX() - entity.getVelocity());
        }

        // Remain/land on ground or start falling
        collisions = detectCollisionsWithWorld(entity, level);
        if (collisions.contains(Collision.UP)) {
            entity.setY(entity.getY() - entity.getVerticalVelocity());
            entity.setVerticalVelocity(BASE_VERTICAL_VELOCITY);
            entity.endJump();
        } else if (collisions.contains(Collision.DOWN)) {
            entity.setY(entity.getY() - entity.getVerticalVelocity());
            entity.setVerticalVelocity(0);
            entity.endJump();
        } else {
            if (entity.getVerticalVelocity() == 0) {
                entity.setVerticalVelocity(BASE_VERTICAL_VELOCITY);
            } else {
                entity.setVerticalVelocity(fakeAcceleration(entity.getVerticalVelocity()));
            }
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
            } else if (isEitherPointInRectangle2(entity.getCollisionPoints()[Collision.UP.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.UP);
                block.bump();
            }
        }
        return collisions;
    }

    public Set<Collision> detectHorizontalCollisionsWithWorld(MovingEntity entity, Level level) {
        Set<Collision> collisions = new HashSet<>(2);

        for (BlockBase block : level.getBlocks()) {
            if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.RIGHT.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.RIGHT);
            } else if (isEitherPointInRectangle(entity.getCollisionPoints()[Collision.LEFT.ordinal()], block.getBoundingRectangle())) {
                collisions.add(Collision.LEFT);
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

    private boolean isEitherPointInRectangle2(Point[] points, Rectangle rectangle) {
        return Arrays.stream(points).anyMatch(rectangle::contains);
    }
}

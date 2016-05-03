package se.tarlinder.platformer.engine.component.ai;

import se.tarlinder.platformer.engine.Collision;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.entity.MovingEntity;

public class HorizontalBouncerAIComponent implements AIComponent {
    public void update(MovingEntity entity) {
        if (entity.getCollision() == Collision.RIGHT && entity.getHorizontalDirection() == HorizontalDirection.RIGHT) {
            entity.setCollision(Collision.NONE);
            entity.setHorizontalDirection(HorizontalDirection.LEFT);
            entity.setVelocity(0);
        } else if (entity.getCollision() == Collision.LEFT && entity.getHorizontalDirection() == HorizontalDirection.LEFT) {
            entity.setCollision(Collision.NONE);
            entity.setHorizontalDirection(HorizontalDirection.RIGHT);
            entity.setVelocity(0);
        }
    }
}

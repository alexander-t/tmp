package se.tarnowski.platformer.engine.component.input;

import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.engine.entity.MovingEntity;
import se.tarnowski.platformer.mario.entity.Goomba;

public class DumbWalkerInputComponent implements InputComponent {

    @Override
    public void update(MovingEntity movingEntity) {
        if (movingEntity.getHorizontalDirection() == HorizontalDirection.RIGHT) {
            movingEntity.setVelocity(Goomba.WALK_ACCELERATION);
        } else if (movingEntity.getHorizontalDirection() == HorizontalDirection.LEFT) {
            movingEntity.setVelocity(-Goomba.WALK_ACCELERATION);
        }
    }
}

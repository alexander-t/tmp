package se.tarlinder.platformer.engine.component.input;

import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.entity.Goomba;

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

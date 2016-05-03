package se.tarlinder.platformer.engine.component.input;

import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.entity.MovingEntity;

public class DumbWalkerInputComponent implements InputComponent {

    private final float walkAcceleration;

    public DumbWalkerInputComponent(float walkAcceleration) {
        this.walkAcceleration = walkAcceleration;
    }

    @Override
    public void update(MovingEntity movingEntity) {
        if (movingEntity.getHorizontalDirection() == HorizontalDirection.RIGHT) {
            movingEntity.setVelocity(walkAcceleration);
        } else if (movingEntity.getHorizontalDirection() == HorizontalDirection.LEFT) {
            movingEntity.setVelocity(-walkAcceleration);
        }
    }
}

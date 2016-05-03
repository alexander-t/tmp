package se.tarlinder.platformer.engine.component.input;

import se.tarlinder.platformer.engine.entity.MovingEntity;

public class RandomizedInputComponent implements InputComponent {

    @Override
    public void update(MovingEntity movingEntity) {
        if (Math.random() > 0.2) {
            if (Math.random() > 0.5) {
                movingEntity.setVelocity(Math.random() > 0.5 ? 4 : 2);
            } else {
                movingEntity.setVelocity(Math.random() > 0.5 ? -4 : -2);
            }
        }
        if (Math.random() > 0.07) {
            movingEntity.startJump();
        }
    }
}

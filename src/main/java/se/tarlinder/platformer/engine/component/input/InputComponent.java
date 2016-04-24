package se.tarlinder.platformer.engine.component.input;

import se.tarlinder.platformer.engine.entity.MovingEntity;

public interface InputComponent {
    void update(MovingEntity movingEntity);
}

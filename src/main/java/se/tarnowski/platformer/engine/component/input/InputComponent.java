package se.tarnowski.platformer.engine.component.input;

import se.tarnowski.platformer.engine.entity.MovingEntity;

public interface InputComponent {
    void update(MovingEntity movingEntity);
}

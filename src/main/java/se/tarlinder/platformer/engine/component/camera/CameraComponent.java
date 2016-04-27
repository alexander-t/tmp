package se.tarlinder.platformer.engine.component.camera;

import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;

public interface CameraComponent {
    void update(MovingEntity entity, GameContext gameContext);
}

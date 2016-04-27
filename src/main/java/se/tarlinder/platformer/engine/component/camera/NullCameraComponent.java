package se.tarlinder.platformer.engine.component.camera;

import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;

public class NullCameraComponent implements CameraComponent {

    @Override
    public void update(MovingEntity entity, GameContext gameContext) {
        // Do nothing
    }
}

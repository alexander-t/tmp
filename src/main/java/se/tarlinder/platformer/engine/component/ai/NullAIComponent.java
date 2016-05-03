package se.tarlinder.platformer.engine.component.ai;

import se.tarlinder.platformer.engine.entity.MovingEntity;

public class NullAIComponent implements AIComponent {

    @Override
    public void update(MovingEntity movingEntity) {
        // Do nothing
    }
}

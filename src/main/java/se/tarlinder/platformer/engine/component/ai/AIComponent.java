package se.tarlinder.platformer.engine.component.ai;

import se.tarlinder.platformer.engine.entity.MovingEntity;

public interface AIComponent {
    void update(MovingEntity movingEntity);
}

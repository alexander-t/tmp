package se.tarlinder.platformer.engine.component;

import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.state.LifeState;

public class DeathComponent {
    public void update(MovingEntity movingEntity) {
        if (movingEntity.getLifeState() == LifeState.DYING) {

        }
    }
}

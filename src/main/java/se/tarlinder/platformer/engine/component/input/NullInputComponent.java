package se.tarlinder.platformer.engine.component.input;

import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.engine.entity.MovingEntity;

/**
 * Input component that does nothing
 */
public class NullInputComponent implements InputComponent {

    @Override
    public void update(MovingEntity movingEntity) {

    }
}

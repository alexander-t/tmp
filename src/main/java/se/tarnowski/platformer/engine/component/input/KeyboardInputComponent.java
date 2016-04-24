package se.tarnowski.platformer.engine.component.input;

import se.tarnowski.platformer.engine.entity.MovingEntity;
import se.tarnowski.platformer.engine.input.swing.KeyAdapterInputHandler;

public class KeyboardInputComponent implements InputComponent {


    private KeyAdapterInputHandler inputHandler;
    private boolean allowJump = true;

    public KeyboardInputComponent(KeyAdapterInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void update(MovingEntity movingEntity) {
        float walkAcceleration = 2;
        float velocity = 0;

        // Sprint
        if (inputHandler.shiftPressed) {
            walkAcceleration *=2;
        }

        if (inputHandler.rightArrowPressed) {
            velocity += walkAcceleration;
        }
        if (inputHandler.leftArrowPressed) {
            velocity -= walkAcceleration;
        }

        if (inputHandler.upArrowPressed && allowJump) {
                allowJump = false;
                movingEntity.startJump();
        } else if (!inputHandler.upArrowPressed) {
            allowJump = true;
        }

        movingEntity.setVelocity(velocity);
    }
}

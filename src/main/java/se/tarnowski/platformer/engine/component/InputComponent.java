package se.tarnowski.platformer.engine.component;

import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.mario.entity.Goomba;

public class InputComponent {
    public void update(Goomba goomba) {
        if (goomba.getHorizontalDirection() == HorizontalDirection.RIGHT) {
            goomba.setVelocity(Goomba.WALK_ACCELERATION);
        } else if (goomba.getHorizontalDirection() == HorizontalDirection.LEFT) {
            goomba.setVelocity(-Goomba.WALK_ACCELERATION);
        }
    }
}

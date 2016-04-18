package se.tarnowski.platformer.engine.input.javafx;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class EventHandlerInputHandler implements EventHandler<KeyEvent> {

    public boolean rightArrowPressed;
    public boolean leftArrowPressed;
    public boolean upArrowPressed;
    public boolean downArrowPressed;
    public boolean shiftPressed;

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyEvent.getCode()) {
                case RIGHT:
                    rightArrowPressed = true;
                    break;
                case LEFT:
                    leftArrowPressed = true;
                    break;
                case UP:
                    upArrowPressed = true;
                    break;
                case DOWN:
                    downArrowPressed = true;
                    break;
                case SHIFT:
                    shiftPressed = true;
                    break;
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyEvent.getCode()) {
                case RIGHT:
                    rightArrowPressed = false;
                    break;
                case LEFT:
                    leftArrowPressed = false;
                    break;
                case UP:
                    upArrowPressed = false;
                    break;
                case DOWN:
                    downArrowPressed = false;
                    break;
                case SHIFT:
                    shiftPressed = false;
                    break;
            }
        }
    }
}

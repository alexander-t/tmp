package se.tarlinder.platformer.engine.input.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyAdapterInputHandler extends KeyAdapter {

    public boolean rightArrowPressed;
    public boolean leftArrowPressed;
    public boolean upArrowPressed;
    public boolean downArrowPressed;
    public boolean shiftPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                rightArrowPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftArrowPressed = true;
                break;
            case KeyEvent.VK_UP:
                upArrowPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downArrowPressed = true;
                break;
            case KeyEvent.VK_SHIFT:
                shiftPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                rightArrowPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftArrowPressed = false;
                break;
            case KeyEvent.VK_UP:
                upArrowPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downArrowPressed = false;
                break;
            case KeyEvent.VK_SHIFT:
                shiftPressed = false;
                break;
        }
    }
}

package se.tarlinder.platformer;

import se.tarnowski.platformer.engine.view.ViewPort;

public class NullViewPort implements ViewPort {
    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public void setCamX(int camX) {
    }

    @Override
    public void setCamY(int camY) {
    }

    @Override
    public void resetCamera() {
    }
}

package se.tarnowski.platformer.engine.view;

public interface ViewPort {
    int getWidth();
    int getHeight();

    void setCamX(int camX);
    void setCamY(int camY);
    void resetCamera();
}

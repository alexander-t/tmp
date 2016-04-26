package se.tarlinder.platformer.engine.view;

public interface ViewPort {
    int getWidth();
    int getHeight();

    void update();

    void setCamX(int camX);
    void setCamY(int camY);
    void resetCamera();
}

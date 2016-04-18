package se.tarnowski.platformer.mario.view.javafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CanvasViewPort extends Canvas {

    private final GraphicsContext gc;
    private int fps;

    private Image backgroundImage = new Image("/background.png", false);

    public CanvasViewPort() {
        super(1024, 768);
        gc = getGraphicsContext2D();
    }

    public void repaint() {
        gc.drawImage(backgroundImage, 0, 0);
        gc.fillText("FPS: " + fps, 10, 20);
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}

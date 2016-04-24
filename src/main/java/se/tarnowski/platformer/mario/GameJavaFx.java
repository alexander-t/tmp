package se.tarnowski.platformer.mario;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.Player;
import se.tarnowski.platformer.mario.view.javafx.CanvasViewPort;
import se.tarnowski.platformer.engine.input.javafx.EventHandlerInputHandler;

public class GameJavaFx extends Application {

    private final Player player;
    private final EventHandlerInputHandler inputHandler = new EventHandlerInputHandler();

    private static final int PLAYER_START_X = BlockBase.BLOCK_SIZE;
    private static final int PLAYER_START_Y = 768 - BlockBase.BLOCK_SIZE - 23;

    public GameJavaFx() {
        player = new Player(PLAYER_START_X, PLAYER_START_Y, null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        CanvasViewPort viewPort = new CanvasViewPort();

        scene.setOnKeyPressed(inputHandler);
        scene.setOnKeyReleased(inputHandler);

        root.getChildren().add(viewPort);
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            int frameCount = 0;
            long startTime = System.nanoTime();
            int fps = 0;

            @Override
            public void handle(long now) {
                frameCount++;

                viewPort.setFps(fps);
                viewPort.repaint();

                if (now - startTime >= 1000000000) {
                    fps = frameCount;
                    frameCount = 0;
                    startTime = System.nanoTime();
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

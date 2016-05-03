package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.DebugFlags;
import se.tarlinder.platformer.engine.component.camera.ScrollViewPortCameraComponent;
import se.tarlinder.platformer.engine.component.input.InputComponent;
import se.tarlinder.platformer.engine.component.input.KeyboardInputComponent;
import se.tarlinder.platformer.engine.input.swing.KeyAdapterInputHandler;
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;
import se.tarlinder.platformer.mario.entity.BlockBase;
import se.tarlinder.platformer.mario.entity.Goomba;
import se.tarlinder.platformer.mario.entity.Player;
import se.tarlinder.platformer.mario.state.LifeState;
import se.tarlinder.platformer.mario.view.swing.SwingViewPort;
import se.tarlinder.platformer.tmx.SimpleTmxLoader;

import java.io.InputStreamReader;

public class Game {

    public static final int MS_PER_FRAME = 16;
    private static final int PLAYER_START_X = BlockBase.BLOCK_SIZE;
    private static final int PLAYER_START_Y = BlockBase.BLOCK_SIZE * 21 - 10;

    private final Player player;

    public Game() {
        SpriteCacheDirector.loadAllSprites();
        final KeyAdapterInputHandler inputHandler = new KeyAdapterInputHandler();
        final InputComponent keyboardInputComponent = new KeyboardInputComponent(inputHandler);
        final Level level = new LevelBuilder(new SimpleTmxLoader(new InputStreamReader(this.getClass().getResourceAsStream("/levels/mario.tmx")))).buildLevel();

        final GameContext gameContext = new GameContext(level);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, gameContext, keyboardInputComponent);
        gameContext.addEntity(player);
        gameContext.addEntity(new Goomba(350, 511, gameContext));


        gameContext.addEntity(new Goomba(370, 511, gameContext)
                .withCameraComponent(new ScrollViewPortCameraComponent())
                .withInputComponent(new KeyboardInputComponent(inputHandler)));

        final SwingViewPort viewPort = new SwingViewPort(gameContext);
        viewPort.addKeyListener(inputHandler);

        new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();

                if (inputHandler.cPressed) {
                    DebugFlags.showCollisionPoints = !DebugFlags.showCollisionPoints;
                }

                gameContext.update();
                viewPort.update();

                if (player.getLifeState() == LifeState.DEAD) {
                    player.respawnAt(PLAYER_START_X, PLAYER_START_Y);
                }

                try {
                    Thread.sleep(Math.max(startTime + MS_PER_FRAME - System.currentTimeMillis(), 0));
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public static void main(String... args) {
        new Game();
    }
}

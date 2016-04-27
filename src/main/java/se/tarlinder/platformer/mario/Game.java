package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.component.camera.NullCameraComponent;
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

    private final Player player;
    private final Level level;
    private final GameContext gameContext;

    private final KeyAdapterInputHandler inputHandler;

    private static final int PLAYER_START_X = BlockBase.BLOCK_SIZE;
    private static final int PLAYER_START_Y = BlockBase.BLOCK_SIZE * 21 - 10;

    public Game() {

        SpriteCache.addSheet("enemies", "/smb_enemies_sheet.png");
        SpriteCache.addSheet("heroes", "/hero.png");
        SpriteCache.add("goomba walk1", "enemies", 0, 4, 16, 16);
        SpriteCache.add("goomba walk2", "enemies", 30, 4, 16, 16);
        SpriteCache.add("mario walk right1", "heroes", 13, 7, 16, 24);
        SpriteCache.add("mario walk right2", "heroes", 30, 7, 16, 24);
        SpriteCache.add("mario walk right3", "heroes", 47, 7, 16, 24);
        SpriteCache.add("mario walk right4", "heroes", 64, 7, 16, 24);
        SpriteCache.add("mario jump right", "heroes", 115, 7, 16, 24);
        SpriteCache.addFlipped("mario walk left1", "mario walk right1");
        SpriteCache.addFlipped("mario walk left2", "mario walk right2");
        SpriteCache.addFlipped("mario walk left3", "mario walk right3");
        SpriteCache.addFlipped("mario walk left4", "mario walk right4");
        SpriteCache.addFlipped("mario jump left", "mario jump right");
        SpriteCache.add("mario dying", "heroes", 411, 7, 16, 24);

        inputHandler = new KeyAdapterInputHandler();
        InputComponent keyboardInputComponent = new KeyboardInputComponent(inputHandler);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, keyboardInputComponent);
        level = new LevelBuilder(new SimpleTmxLoader(new InputStreamReader(this.getClass().getResourceAsStream("/levels/mario.tmx")))).buildLevel();
        gameContext = new GameContext(player, level);

        gameContext.addEnemy(new Goomba(350, 511, gameContext));

        /*
        gameContext.addEnemy(new Goomba(350, 511, gameContext)
                .withCameraComponent(new ScrollViewPortCameraComponent())
                .withInputComponent(new KeyboardInputComponent(inputHandler)));
        */
        final SwingViewPort viewPort = new SwingViewPort(gameContext);
        viewPort.addKeyListener(inputHandler);

        new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();
                update();
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

    private void update() {
        gameContext.update();
    }

    public static void main(String... args) {
        new Game();
    }
}

package se.tarnowski.platformer.mario;

import se.tarnowski.platformer.engine.input.swing.KeyAdapterInputHandler;
import se.tarnowski.platformer.engine.sprite.swing.SpriteCache;
import se.tarnowski.platformer.mario.entity.BlockBase;
import se.tarnowski.platformer.mario.entity.Goomba;
import se.tarnowski.platformer.mario.entity.Player;
import se.tarnowski.platformer.mario.state.LifeState;
import se.tarnowski.platformer.mario.view.swing.JPanelViewPort;

import javax.swing.*;
import java.util.List;

public class Game extends JFrame {

    public static final int MS_PER_FRAME = 16;

    private final Player player;
    private final Level level;
    private final GameContext gameContext;

    private final KeyAdapterInputHandler inputHandler;
    private boolean allowJump = true;

    private static final int PLAYER_START_X = BlockBase.BLOCK_SIZE;
    private static final int PLAYER_START_Y = BlockBase.BLOCK_SIZE * 10;

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

        player = new Player(PLAYER_START_X, PLAYER_START_Y);
        level = LevelBuilder.buildLevel();
        gameContext = new GameContext(player, level);
        gameContext.addEnemy(new Goomba(680, 511, gameContext));
        gameContext.addEnemy(new Goomba(300, 511, gameContext));
        inputHandler = new KeyAdapterInputHandler();

        final JPanelViewPort viewPort = new JPanelViewPort(gameContext);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(inputHandler);
        add(viewPort);
        pack();
        setVisible(true);

        new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();

                processInput();
                update();

                viewPort.repaint();

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

    private void processInput() {
        if (inputHandler.shiftPressed) {
            player.sprint();
        } else {
            player.walk();
        }

        // Don't else if here. If two opposite directions are pressed, that's the player's problem
        if (inputHandler.rightArrowPressed) {
            player.moveRight();
        }

        if (inputHandler.leftArrowPressed) {
            player.moveLeft();
        }

        if (inputHandler.upArrowPressed && allowJump) {
            allowJump = false;
            player.jump();
        } else if (!inputHandler.upArrowPressed){
            allowJump = true;
        }
    }

    private void update() {
        gameContext.update();
    }

    public static void main(String... args) {
        new Game();
    }
}

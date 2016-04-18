package se.tarnowski.platformer.mario.entity;

import se.tarnowski.platformer.mario.GameContext;
import se.tarnowski.platformer.engine.entity.Entity;
import se.tarnowski.platformer.engine.HorizontalDirection;
import se.tarnowski.platformer.engine.VerticalDirection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Goomba extends Entity {

    private float dx = 0.70f;
    private HorizontalDirection horizontalDirection = HorizontalDirection.RIGHT;

    private GameContext gameContext;

    private List<String> frames = new ArrayList<>();
    private String currentImageId;
    private int frameIndex;

    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    public Goomba(int x, int y, GameContext gameContext) {
        super(x, y);
        this.gameContext = gameContext;

        frames.add("goomba walk1");
        frames.add("goomba walk2");

        currentImageId = frames.get(0);
    }

    @Override
    public String getCurrentImageId() {
        return currentImageId;
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    @Override
    public void update() {
        if (frameIndex < 15) {
            currentImageId = frames.get(0);
            frameIndex++;
        } else if (frameIndex < 30) {
            currentImageId = frames.get(1);
            frameIndex++;
        } else {
            frameIndex = 0;
        }

        if (horizontalDirection == HorizontalDirection.RIGHT) {
            if (gameContext.detectCollisionsWithWorld(new Rectangle((int) (x + dx), (int) y, SPRITE_WIDTH, SPRITE_HEIGHT), VerticalDirection.NONE)) {
                horizontalDirection = horizontalDirection.LEFT;
            } else {
                x += dx;
            }
        } else if (horizontalDirection == HorizontalDirection.LEFT) {
            if (gameContext.detectCollisionsWithWorld(new Rectangle((int) (x - dx), (int) y, SPRITE_WIDTH, SPRITE_HEIGHT), VerticalDirection.NONE)) {
                horizontalDirection = horizontalDirection.RIGHT;
            } else {
                x -= dx;
            }
        }
    }
}

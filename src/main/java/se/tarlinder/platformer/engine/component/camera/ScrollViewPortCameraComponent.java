package se.tarlinder.platformer.engine.component.camera;

import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.mario.GameContext;

public class ScrollViewPortCameraComponent implements CameraComponent {

    public void update(MovingEntity entity, GameContext gameContext) {
        final int viewPortWidth = gameContext.getViewPortWidth();
        final int viewPortHeight = gameContext.getViewPortHeight();


        if (entity.getX() >= viewPortWidth / 2) {
            if (entity.getX() < gameContext.getLevel().widthInPixels() - viewPortWidth / 2) {
                gameContext.getViewPort().setCamX((int) entity.getX() - gameContext.getViewPort().getWidth() / 2);
            }
        }

        if (entity.getY() >= viewPortHeight / 2) {
            if (entity.getY() < gameContext.getLevel().heightInPixels() - viewPortHeight / 2) {
                gameContext.getViewPort().setCamY((int) entity.getY() - gameContext.getViewPort().getHeight() / 2);
            }
        }

    }
}
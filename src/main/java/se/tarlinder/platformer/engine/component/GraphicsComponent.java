package se.tarlinder.platformer.engine.component;

import se.tarlinder.platformer.engine.Animation;
import se.tarlinder.platformer.engine.HorizontalDirection;
import se.tarlinder.platformer.engine.VerticalDirection;
import se.tarlinder.platformer.engine.entity.MovingEntity;

public class GraphicsComponent {

    public String update(MovingEntity entity, boolean hasChangeDirection) {

        Animation animation = entity.getAnimation();

        if (entity.isJumping()) {
                //(entity.getVerticalDirection() == VerticalDirection.DOWN && entity.getVelocity() > 0)) {

            // Jumping or falling...
            return getJumpingImageId(entity);
        } else {
            if (entity.getVelocity() > 0) {
                animation = entity.getWalkRightAnimation();
                if (hasChangeDirection) {
                    animation.reset();
                } else {
                    animation.advance();
                }
            } else if (entity.getVelocity() < 0) {
                animation = entity.getWalkLeftAnimation();
                if (hasChangeDirection) {
                    animation.reset();
                } else {
                    animation.advance();
                }
            } else {
                animation.reset();
            }
        }
        entity.setAnimation(animation);
        return animation.getCurrentImageId();
    }

    private String getJumpingImageId(MovingEntity entity) {
        String imageId = entity.getHorizontalDirection() == HorizontalDirection.RIGHT
                ? entity.getJumpRightImageId() : entity.getJumpLeftImageId();
        if (imageId == null) {

            // Non-jumping entities don't have jumping image ids. Don't make a big deal and use a walking image id
            imageId = entity.getAnimation().getCurrentImageId();
        }
        return imageId;
    }
}

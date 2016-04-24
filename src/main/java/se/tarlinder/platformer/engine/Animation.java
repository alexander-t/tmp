package se.tarlinder.platformer.engine;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<AnimationStep> animationSteps = new ArrayList<>();
    private int currentFrame = 1;
    private int totalNumberOfFrames = 0;

    public Animation add(String spriteId, int nofFrames) {
        animationSteps.add(new AnimationStep(spriteId, nofFrames));
        totalNumberOfFrames += nofFrames;
        return this;
    }

    public String getCurrentImageId() {
        int absoluteFrame = 0;
        for (AnimationStep animationStep : animationSteps) {
            absoluteFrame += animationStep.getFrames();
            if (currentFrame <= absoluteFrame) {
                return animationStep.getSpriteId();
            }
        }
        throw new IllegalStateException();
    }

    public void advance() {
        currentFrame++;
        currentFrame %= totalNumberOfFrames + 1;
    }

    public void reset() {
        currentFrame = 1;
    }

    private static class AnimationStep {
        private final String spriteId;
        private final int frames;

        public AnimationStep(String spriteId, int frames) {
            this.spriteId = spriteId;
            this.frames = frames;
        }

        public int getFrames() {
            return frames;
        }

        public String getSpriteId() {
            return spriteId;
        }
    }
}

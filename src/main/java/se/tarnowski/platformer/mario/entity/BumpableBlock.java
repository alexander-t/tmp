package se.tarnowski.platformer.mario.entity;

public class BumpableBlock extends BlockBase {

    private boolean bumped;
    private int bumpTicks;

    public BumpableBlock(int x, int y, String imageId) {
        super(x, y, imageId);
    }

    public void bump() {
        if (!bumped) {
            bumped = true;
            bumpTicks = 0;
        }
    }

    @Override
    public void update() {
        if (bumped) {
            switch (bumpTicks++) {
                case 0:
                    y -= 4;
                    break;
                case 1:
                    break;
                case 2:
                    y += 4;
                    break;
                default:
                    bumped = false;
            }
        }
    }
}

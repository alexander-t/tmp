package se.tarlinder.platformer.mario.view.swing;

import se.tarlinder.platformer.engine.DebugFlags;
import se.tarlinder.platformer.engine.entity.MovingEntity;
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;
import se.tarlinder.platformer.engine.view.ViewPort;
import se.tarlinder.platformer.mario.GameContext;
import se.tarlinder.platformer.mario.entity.BlockBase;

import javax.swing.*;
import java.awt.*;

public class SwingViewPort extends JFrame implements ViewPort {

    private int camX = 0;
    private int camY = 0;
    private JPanel viewPort;
    private Image backgroundImage;

    public SwingViewPort(GameContext gameContext) {
        gameContext.setViewPort(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        viewPort = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.translate(-camX, -camY);
                g.drawImage(backgroundImage, camX / 5, camY / 5, null);

                for (BlockBase block : gameContext.getLevel().getBlocks()) {
                    g.drawImage(SpriteCache.get(block.getCurrentImageId()), (int) block.getX(), (int) block.getY(), null);
                }

                for (MovingEntity entity : gameContext.movingEntities) {
                    g.drawImage(SpriteCache.get(entity.getCurrentImageId()), (int) entity.getX(), (int) entity.getY(), null);
                    if (DebugFlags.showCollisionPoints) {
                        drawCollisionPoints(entity, g);
                    }
                }

                g.setColor(Color.lightGray);
                g.fill3DRect(camX, camY, getWidth(), 16, true);
                g.setColor(Color.black);
//                g.drawString(String.format("x=%d, y=%d", (int) player.getX(), (int) player.getY()), camX + 4, camY + 12);
            }

            private void drawCollisionPoints(MovingEntity entity, Graphics g) {
                g.setColor(Color.red);
                MovingEntity movingEntity = (MovingEntity) entity;
                for (int cpy = 0; cpy < movingEntity.getCollisionPoints().length; cpy++) {
                    for (int cpx = 0; cpx < movingEntity.getCollisionPoints()[0].length; cpx++) {
                        g.drawLine(movingEntity.getCollisionPoints()[cpy][cpx].x, movingEntity.getCollisionPoints()[cpy][cpx].y,
                                movingEntity.getCollisionPoints()[cpy][cpx].x, movingEntity.getCollisionPoints()[cpy][cpx].y);
                    }
                }

            }
        };
        viewPort.setPreferredSize(new Dimension(BlockBase.BLOCK_SIZE * 32, BlockBase.BLOCK_SIZE * 22));
        backgroundImage = ImageUtils.loadImageResource("/sprites/realistic/sky.jpg");
        add(viewPort);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void setCamX(int camX) {
        this.camX = camX;
    }

    @Override
    public void setCamY(int camY) {
        this.camY = camY;
    }

    @Override
    public void resetCamera() {
        camX = camY = 0;
    }
}

package se.tarlinder.platformer.mario.view.swing;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static BufferedImage loadImageResource(String resourcePath) {
        try {
            return ImageIO.read(ImageUtils.class.getResourceAsStream(resourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage loadImageFile(File imageFile) {
        try {
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static BufferedImage flipImage(BufferedImage srcImage) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-srcImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(srcImage, null);
    }

}

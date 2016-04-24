package se.tarlinder.platformer.engine.sprite.swing;

import se.tarlinder.platformer.mario.view.swing.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteCache {

    private static Map<String, BufferedImage> sheets = new HashMap<>();
    private static Map<String, BufferedImage> sprites = new HashMap<>();

    public static void addSheet(String id, String resourcePath) {
        sheets.put(id, ImageUtils.loadImageResource(resourcePath));
    }

    public static void addImage(String id, String resourcePath) {
        sprites.put(id, ImageUtils.loadImageResource(resourcePath));
    }

    public static void add(String id, String sheetId, int x, int y, int w, int h) {
        BufferedImage sheet = sheets.get(sheetId);
        if (sheetId == null)
        {
            throw new IllegalArgumentException("No sheet with that id");
        }

        sprites.put(id, sheet.getSubimage(x, y, w, h));
    }

    public static void addFlipped(String newId, String existingId) {
        sprites.put(newId, ImageUtils.flipImage(get(existingId)));
    }

    public static BufferedImage get(String id) {
        return sprites.getOrDefault(id, null);
    }
}

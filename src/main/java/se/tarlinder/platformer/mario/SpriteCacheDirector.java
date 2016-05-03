package se.tarlinder.platformer.mario;

import se.tarlinder.platformer.engine.sprite.swing.SpriteCache;

public class SpriteCacheDirector {
    public static void loadAllSprites() {
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
    }
}

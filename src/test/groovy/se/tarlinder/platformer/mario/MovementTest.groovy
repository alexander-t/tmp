package se.tarlinder.platformer.mario

import se.tarlinder.platformer.StringLevelBuilder
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache
import se.tarlinder.platformer.mario.entity.BlockBase
import se.tarlinder.platformer.mario.entity.Player
import se.tarlinder.platformer.mario.view.swing.SwingViewPort
import spock.lang.Specification

class MovementTest extends Specification {
    def "A player standing still on a block won't move anywhere"() {
        given: "A single block"
        SpriteCache.addImage("B", "/sprites/smb1/block1_24x24.png");
        SpriteCache.addSheet("heroes", "/hero.png");
        SpriteCache.add("mario walk right1", "heroes", 13, 7, 16, 24);

        and: "The player standing on top of it"
        def player = new Player(BlockBase.BLOCK_SIZE, 24)
        def level = new StringLevelBuilder().buildLevel((String[])[
                "I  ",
                "I  ",
                "III"].toArray())
        def gameContext = new GameContext(player, level)
        def viewPort = new SwingViewPort(gameContext)
        gameContext.setViewPort(viewPort)

        when: "Time is advanced"
        10.times {player.update(); viewPort.repaint();Thread.sleep(100)}

        then: "The player hasn't moved horizontally"
        player.getX() == BlockBase.BLOCK_SIZE

        and: "The player hasn't moved vertically"
        player.getY() == 24
    }

}

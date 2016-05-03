package se.tarlinder.platformer.mario

import se.tarlinder.platformer.engine.component.input.NullInputComponent
import se.tarlinder.platformer.NullViewPort
import se.tarlinder.platformer.StringLevelBuilder
import se.tarlinder.platformer.engine.component.input.RandomizedInputComponent
import se.tarlinder.platformer.engine.sprite.swing.SpriteCache
import se.tarlinder.platformer.mario.entity.BlockBase
import se.tarlinder.platformer.mario.entity.Player
import se.tarlinder.platformer.mario.view.swing.SwingViewPort
import spock.lang.Specification

import static se.tarlinder.platformer.util.Slomo.slomo

class MovementTest extends Specification {

    def setupSpec() {
        SpriteCacheDirector.loadAllSprites();
        SpriteCache.addImage("I", "/sprites/smb1/block1_24x24.png");
        SpriteCache.addImage("B", "/sprites/smb1/block2_24x24.png");
    }

    def "A player standing still on a block won't move anywhere"() {
        given: "A simple level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                "   ",
                "   ",
                "III"].toArray())
        def gameContext = new GameContext(level)

        and: "The player standing on top of it"
        final int startX = BlockBase.BLOCK_SIZE;
        final int startY = BlockBase.BLOCK_SIZE + 1; // Add +1 to y because of sprite's collision points
        def player = new Player(startX, startY, gameContext, new NullInputComponent())
        gameContext.addEntity(player)

        def viewPort = new NullViewPort()
        gameContext.setViewPort(viewPort)

        when: "Time is advanced"
        10.times { player.update(); viewPort.update(); }

        then: "The player hasn't moved"
        player.getX() == startX
        player.getY() == startY
    }

    def "A player standing still on a block won't move anywhere with visual aids"() {
        given: "A simple level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                "   ",
                "   ",
                "III"].toArray())
        def gameContext = new GameContext(level)

        and: "The player standing on top of it"
        final int startX = BlockBase.BLOCK_SIZE;
        final int startY = BlockBase.BLOCK_SIZE + 1; // Add +1 to y because of sprite's collision points
        def player = new Player(startX, startY, gameContext, new NullInputComponent())
        gameContext.addEntity(player)

        def viewPort = new SwingViewPort(gameContext)
        gameContext.setViewPort(viewPort)

        when: "Time is advanced"
        10.times { slomo { player.update(); viewPort.update(); } }

        then: "The player hasn't moved"
        player.getX() == startX
        player.getY() == startY
    }

    def "dsA player standing still on a block won't move anywhere with visual aids"() {
        given: "A simple level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                "        ",
                "I      I",
                "I  B   I",
                "I      I ",
                "I   BB I ",
                "I B    I",
                "I    III",
                "IIIIIIII"].toArray())
        def gameContext = new GameContext(level)

        and: "The player standing on top of it"
        final int startX = BlockBase.BLOCK_SIZE;
        final int startY = BlockBase.BLOCK_SIZE * 2 + 1; // Add +1 to y because of sprite's collision points
        def player = new Player(startX, startY, gameContext, new RandomizedInputComponent())
        gameContext.addEntity(player)

        def viewPort = new SwingViewPort(gameContext)
        gameContext.setViewPort(viewPort)

        when: "Time is advanced"
        10000.times { slomo {player.update(); viewPort.update();} }

        then:
        player.getY() > 0
        player.getX() < 9 * BlockBase.BLOCK_SIZE
    }
}
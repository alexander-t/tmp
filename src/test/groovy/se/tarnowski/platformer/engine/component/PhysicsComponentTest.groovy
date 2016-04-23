package se.tarnowski.platformer.engine.component

import se.tarlinder.platformer.StringLevelBuilder
import se.tarnowski.platformer.mario.Level
import se.tarnowski.platformer.mario.entity.BlockBase
import se.tarnowski.platformer.mario.entity.Goomba
import spock.lang.Specification
import spock.lang.Subject

class PhysicsComponentTest extends Specification {

    @Subject
    def physicsComponent = new PhysicsComponent()

    def "A goomba placed in mid-air will start falling"() {
        given:
        def emptyLevel = new Level(0, 0, [])
        def fallingGoomba = new Goomba(0, 0, null)

        when: "Time is advanced by two frames"
        2.times { physicsComponent.update(fallingGoomba, emptyLevel) }

        then: "The goomba has started falling in the second frame"
        fallingGoomba.getVerticalVelocity() > 1.0f
        fallingGoomba.getY() == PhysicsComponent.BASE_VERTICAL_VELOCITY
    }

    def "A goomba standing on the ground will remain there"() {
        given: "A level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                " ",
                "I"].toArray())

        and: "A stationary goomba standing on it"
        final int groundY = BlockBase.BLOCK_SIZE - Goomba.SPRITE_HEIGHT
        def stationaryGoomba = new Goomba(0, groundY, null)
        stationaryGoomba.setVelocity(0)

        when: "Time is advanced"
        1000.times {physicsComponent.update(stationaryGoomba, level)}

        then: "The goomba's vertical postion doesn't change"
        stationaryGoomba.getY() == groundY

        and: "It has no vertical velocity"
        stationaryGoomba.getVerticalVelocity() == 0.0f
    }

    def "A goomba starting in mid-air above some ground will eventually land"() {
        given: "A level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                " ",
                " ",
                "I"].toArray())

        and: "A goomba in mid-air"
        def fallingGoomba = new Goomba(0, 0, null)
        fallingGoomba.setVelocity(0)

        when: "Time is advanced"
        1000.times {physicsComponent.update(fallingGoomba, level)}

        then: "The goomba lands on the ground"
        (int) fallingGoomba.getY() == BlockBase.BLOCK_SIZE * 2 - Goomba.SPRITE_HEIGHT - 1
        // Don't test the vertical velocity, since it'll oscillate on even/odd frames
    }

}

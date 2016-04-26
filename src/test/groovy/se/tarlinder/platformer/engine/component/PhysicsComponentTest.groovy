package se.tarlinder.platformer.engine.component

import se.tarlinder.platformer.StringLevelBuilder
import se.tarlinder.platformer.mario.Level
import se.tarlinder.platformer.mario.entity.BlockBase
import se.tarlinder.platformer.mario.entity.Goomba
import spock.lang.Specification
import spock.lang.Subject

import static org.hamcrest.number.OrderingComparison.greaterThan
import static spock.util.matcher.HamcrestSupport.expect

class PhysicsComponentTest extends Specification {

    @Subject
    def physicsComponent = new PhysicsComponent()

    def "A Goomba placed in mid-air will start falling"() {
        given: "An empty level and a Goomba floating in mid-air"
        def emptyLevel = new Level(10, 10, [])
        def fallingGoomba = new Goomba(0, 0, null)

        when: "Time is advanced by two frames"
        2.times { physicsComponent.update(fallingGoomba, emptyLevel) }

        then: "The Goomba has started falling in the second frame"
        fallingGoomba.getVerticalVelocity() > PhysicsComponent.BASE_VERTICAL_VELOCITY
        fallingGoomba.getY() == PhysicsComponent.BASE_VERTICAL_VELOCITY
    }

    def "A Goomba standing on the ground will remain there"() {
        given: "A level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                " ",
                "I"].toArray())

        and: "A stationary Goomba standing on it"
        final int groundY = BlockBase.BLOCK_SIZE - Goomba.HEIGHT
        def stationaryGoomba = new Goomba(0, groundY, null)
        stationaryGoomba.setVelocity(0)

        when: "Time is advanced"
        1000.times {physicsComponent.update(stationaryGoomba, level)}

        then: "The Goomba's vertical postion doesn't change"
        stationaryGoomba.getY() == groundY

        and: "It has no vertical velocity"
        stationaryGoomba.getVerticalVelocity() == 0.0f
    }

    def "A Goomba starting in mid-air above some ground will eventually land"() {
        given: "A level with some ground"
        def level = new StringLevelBuilder().buildLevel((String[]) [
                " ",
                " ",
                "I"].toArray())

        and: "A Goomba in mid-air"
        def fallingGoomba = new Goomba(0, 0, null)
        fallingGoomba.setVelocity(0)

        when: "Time is advanced"
        1000.times {physicsComponent.update(fallingGoomba, level)}

        then: "The Goomba lands on the ground"
        (int) fallingGoomba.getY() == BlockBase.BLOCK_SIZE * 2 - Goomba.HEIGHT - 1
        // Don't test the vertical velocity, since it'll oscillate on even/odd frames
    }

}

package se.tarlinder.platformer.engine.component

import se.tarlinder.platformer.StringLevelBuilder
import se.tarlinder.platformer.mario.Level
import se.tarlinder.platformer.mario.entity.BlockBase
import se.tarlinder.platformer.mario.entity.Goomba
import spock.lang.Specification
import spock.lang.Subject

import static org.hamcrest.number.OrderingComparison.greaterThan
import static spock.util.matcher.HamcrestSupport.expect

class STDPhysicsComponentTest extends Specification {

    @Subject
    def physicsComponent = new PhysicsComponent()

    def "A Goomba placed in mid-air will start falling (Demo multiple when/then)"() {
        given: "An empty level and a Goomba floating in mid-air"
        def emptyLevel = new Level(10, 10, [])
        def fallingGoomba = new Goomba(0, 0, null)

        when:
        physicsComponent.update(fallingGoomba, emptyLevel)

        then:
        fallingGoomba.getVerticalVelocity() == PhysicsComponent.BASE_VERTICAL_VELOCITY
        fallingGoomba.getY() == 0

        when:
        physicsComponent.update(fallingGoomba, emptyLevel)

        then:
        fallingGoomba.getVerticalVelocity() > PhysicsComponent.BASE_VERTICAL_VELOCITY
        fallingGoomba.getY() == PhysicsComponent.BASE_VERTICAL_VELOCITY
    }

    def "A Goomba placed in mid-air will start falling (Demo multiple and)"() {
        given: "An empty level"
        def emptyLevel = new Level(10, 10, [])

        and:  "A Goomba floating in mid-air"
        def fallingGoomba = new Goomba(0, 0, null)

        when: "The time is adanced by one frame"
        physicsComponent.update(fallingGoomba, emptyLevel)

        and: "The time is advanced by another frame"
        physicsComponent.update(fallingGoomba, emptyLevel)

        then: "The Goomba has started accelerating"
        fallingGoomba.getVerticalVelocity() > PhysicsComponent.BASE_VERTICAL_VELOCITY

        and: "It has fallen some distance"
        fallingGoomba.getY() > old(fallingGoomba.getY())
    }

    def "A Goomba placed in mid-air will start falling (Demo with() and expect)"() {
        given:
        def emptyLevel = new Level(10, 10, [])
        def fallingGoomba = new Goomba(0, 0, null)

        when:
        5.times { physicsComponent.update(fallingGoomba, emptyLevel) }

        then:
        with(fallingGoomba) {
            expect getVerticalVelocity(), greaterThan(PhysicsComponent.BASE_VERTICAL_VELOCITY)
            expect getY(), greaterThan(PhysicsComponent.BASE_VERTICAL_VELOCITY)
        }
    }
}

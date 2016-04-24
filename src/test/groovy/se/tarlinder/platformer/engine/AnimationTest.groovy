package se.tarlinder.platformer.engine;

import spock.lang.Specification
import spock.lang.Unroll;

public class AnimationTest extends Specification {

    def "Advance animation by one frame"() {
        given: "An animation with two one-frame images"
        def testedAnimation = new Animation()
        testedAnimation.add("one", 1).add("two", 1)

        when: "Animation is advanced by one frame"
        testedAnimation.advance()

        then: "The animation has changed to the second frame"
        testedAnimation.currentImageId == "two"
    }

    def "Cycle animation"() {
        given: "An animation with two one-frame images"
        def testedAnimation = new Animation()
        testedAnimation.add("one", 1).add("two", 1)

        when: "Animation is advanced by two frames"
        2.times { testedAnimation.advance() }

        then: "The animation has been cycled"
        testedAnimation.getCurrentImageId() == "one"
    }

    // Conference
    @Unroll("Advance #ticks and expect #expectedId")
    def "Examine every single frame in an animation"() {
        given:
        def testedAnimation = new Animation()
        testedAnimation.add("one", 1).add("two", 2).add("three", 3)

        when:
        ticks.times {testedAnimation.advance()}

        then:
        testedAnimation.getCurrentImageId() == expectedId

        where:
        ticks   || expectedId
        0       || "one"
        1       || "two"
        2       || "two"
        3       || "three"
        4       || "three"
        5       || "three"
        6       || "one"
    }

    def "Examine every single frame in an animation 2"() {
        given:
        def testedAnimation = new Animation()
        testedAnimation.add("one", 1).add("two", 2).add("three", 3)

        when:
        ticks.times {testedAnimation.advance()}

        then:
        testedAnimation.getCurrentImageId() == expectedId

        where:
        ticks << (0..6)
        expectedId << ["one", ["two"].multiply(2), ["three"].multiply(3), "one"].flatten()
    }
}

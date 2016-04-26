package se.tarlinder.platformer.engine

import spock.lang.Narrative
import spock.lang.Specification

@Narrative("""This tests shows that Spock data pipes
can be used to create arbitrary complex value generators.
This opens up for generative testing!""")
class STDAnimationTest extends Specification {
    def "Examine every single frame in an animation 2"() {
        given:
        def testedAnimation = new Animation()
        testedAnimation.add("one", 1).add("two", 2).add("three", 3)

        when:
        ticks.times { testedAnimation.advance() }

        then:
        testedAnimation.getCurrentImageId() == expectedId

        where:
        ticks << (0..6)
        expectedId << ["one", ["two"].multiply(2), ["three"].multiply(3), "one"].flatten()
    }
}

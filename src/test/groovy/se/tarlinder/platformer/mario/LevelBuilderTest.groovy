package se.tarlinder.platformer.mario

import se.tarlinder.platformer.tmx.SimpleTmxLoader
import spock.lang.Specification

class LevelBuilderTest extends Specification {
    def "Level dimensions are acquired from the TMX loader" () {
        given:
        def tmxLoaderStub = Stub(SimpleTmxLoader)
        tmxLoaderStub.getLevel() >> new int[10][20]
        //tmxLoaderStub.getLevel() >>> [new int[10][20], new int[1][1]]
        tmxLoaderStub.getMapHeight(_) >> 10
        tmxLoaderStub.getMapWidth() >> 20

        when:
        def level = new LevelBuilder(tmxLoaderStub).buildLevel()

        then:
        level.heightInBlocks == 11
        level.widthInBlocks == 20
    }

    def mock() {
        given:
        def a = Mock(List)

        when:
        a.add("dsd")

        then:
        0 * a.add(_)
    }
}

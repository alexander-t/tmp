package se.tarlinder.platformer.mario

import se.tarlinder.platformer.tmx.SimpleTmxLoader
import spock.lang.Specification

class STDLevelBuilderTest extends Specification {

    def "Level dimensions are acquired from the TMX loader" () {

        final levelWidth = 20;
        final levelHeight = 10;

        given:
        def tmxLoaderStub = Stub(SimpleTmxLoader)
        tmxLoaderStub.getLevel() >> new int[levelHeight][levelWidth]
        tmxLoaderStub.getMapHeight() >> levelHeight
        tmxLoaderStub.getMapWidth() >> levelWidth

        when:
        def level = new LevelBuilder(tmxLoaderStub).buildLevel()

        then:
        level.heightInBlocks == levelHeight
        level.widthInBlocks == levelWidth
    }
}

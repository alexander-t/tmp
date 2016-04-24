package se.tarlinder.platformer.tmx

import spock.lang.Specification

class TmxLoaderTest extends Specification {
    def "Loading a small tilemap"() {
        when: "A simple level is loaded"
        def testedLoader = new SimpleTmxLoader(new InputStreamReader(getClass().getResourceAsStream("/small.tmx")))

        then: "Level dimensions are set"
        testedLoader.mapHeight == 3
        testedLoader.mapWidth == 3
        and: "The raw map data is read"
        testedLoader.level == [[0, 1, 0], [0, 2, 2], [1, 0, 2]]
        and: "The tileset contains a tile without properties"
        testedLoader.tiles[1].id == 0
        testedLoader.tiles[1].properties == [:]
        and: "The tileset contains a tile with two properties"
        testedLoader.tiles[2].id == 1
        testedLoader.tiles[2].properties == ["property1": "stringValue", "property2":"true"]
    }
}

package se.tarlinder.platformer.tmx

/**
 * This is a very simple TMX file loader. It assumes that:
 * 1) The map is orthogonal
 * 2) There's one tileset
 * 3) There's one layer
 * 4) CSV map data
 */
class SimpleTmxLoader {

    private int[][] mapData;
    private int mapWidth;
    private int mapHeight;
    private tiles = [:]

    SimpleTmxLoader(Reader reader) {

        def mapNode = new XmlSlurper().parse(reader)
        mapWidth = Integer.valueOf(mapNode.@width.text())
        mapHeight = Integer.valueOf(mapNode.@height.text())
        mapData = new int[mapHeight][mapWidth]

        if (mapNode.tileset.size() > 1) {
            throw new IllegalArgumentException("Only one tileset supported!")
        }
        def firstGid = Integer.valueOf(mapNode.tileset[0].@firstgid.text())

        tiles = mapNode.tileset.tile.collectEntries { t ->
            [firstGid + Integer.valueOf(t.@id.text()),
             new TmxTile(
                     id: Integer.valueOf(t.@id.text()),
                     image: t.image.@source.text(),
                     properties: t?.properties?.property.collectEntries { p -> [p.@name.text(), p.@value.text()] })]
        }

        def tileIds = mapNode.layer.data.text().split(",")
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                mapData[y][x] = Integer.valueOf(tileIds[y * mapWidth + x].trim())
            }
        }
    }

    int[][] getLevel() {
        mapData;
    }

    int getMapHeight() {
        mapHeight;
    }

    int getMapWidth() {
        mapWidth;
    }

    Map<Integer, TmxTile> getTiles() {
        tiles
    }
}



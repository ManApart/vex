class Map {
    private val tiles = listOf(
            listOf(0,0,0),
            listOf(0,0,1),
            listOf(1,1,1)
    )

    fun getTile(x: Float, y: Float): Int {
        return getTile(x.toInt(), y.toInt())
    }

    fun getTile(x: Int, y: Int): Int {
        if (x < 0 || x >= tiles.size || y < 0 || y >= tiles[x].size) {
            return 0
        }
        return tiles[x][y]
    }

    fun getSize() : Int {
        return tiles.size
    }
}
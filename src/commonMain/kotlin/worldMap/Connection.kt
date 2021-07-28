package worldMap


class Connection(val source: Exit, val destination: Exit) {
    constructor(
        levelIdA: Int,
        exitIdA: Int,
        levelIdB: Int,
        exitIdB: Int,
        levels: List<Exit>
    ) : this(
        levels.first { it.level.id == levelIdA && it.id == exitIdA },
        levels.first { it.level.id == levelIdB && it.id == exitIdB })

    var unlocked = true

    init {
        source.connections.add(this)
        destination.connections.add(this)
    }

    fun getOther(exit: Exit): Exit {
        return if (exit == source) destination else source
    }


}
package worldMap


class Connection(val source: Exit, val destination: Exit) {
    constructor(levelIdA: Int,
                exitIdA: Int,
                levelIdB: Int,
                exitIdB: Int,
                levels: List<Exit>
    ) : this(levels.first { it.level.id == levelIdA && it.exitId == exitIdA }, levels.first { it.level.id == levelIdB && it.exitId == exitIdB })

    var unlocked = false
//    val angle = getAngle(source.bounds.source(), destination.bounds.source())


}
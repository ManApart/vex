package level

val levels = buildLevels()

private fun buildLevels(): Map<Int, LevelTemplate> {
    return listOf(
        LevelTemplate(0, "Test Level", "test"),
        LevelTemplate(1, "Test Level 2", "small"),
        LevelTemplate(2, "Test Level 3", "flat")
    ).associateBy { it.id }
}
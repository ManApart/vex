enum class TileTemplateType(val solid: Int, val description: String) {
    S(0, "Start"),
    G(0, "Goal"),
    GG(1, "Solid Goal"),
    O(0, "Open Space"),
    C(1, "Closed Solid")

}
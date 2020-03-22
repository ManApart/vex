package input

interface Input {
    fun update()
    fun keyPressed(key: Int, action: Int)
}
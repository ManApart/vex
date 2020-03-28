package input

interface Input {
    fun update(deltaTime: Float)
    fun keyPressed(key: Int, action: Int)
}
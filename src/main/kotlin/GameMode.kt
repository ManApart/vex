interface GameMode {
    fun init()
    fun processInput(deltaTime: Float)
    fun render()
    fun afterRender(deltaTime: Float)
}
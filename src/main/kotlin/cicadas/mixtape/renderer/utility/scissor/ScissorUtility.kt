package cicadas.mixtape.renderer.utility.scissor

object ScissorUtility {
    private val state = ScissorState()

    fun push(x: Int, y: Int, width: Int, height: Int) = state.push(x, y, width, height)

    fun pop() = state.pop()

    fun copyState() = state.copy()
}
package cicadas.mixtape.rendertest.animation

interface IAnimation {
    fun update(newValue: Float): Float
    fun value(): Float
    fun reverse(): Float
    fun reset(newValue: Float)
}
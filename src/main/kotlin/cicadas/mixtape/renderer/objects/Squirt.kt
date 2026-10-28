package cicadas.mixtape.renderer.objects

open class Squirt(
    val leftBottomSquirt: Float,
    val leftTopSquirt: Float,
    val rightTopSquirt: Float,
    val rightBottomSquirt: Float
) {
    companion object {
        @JvmField
        val EMPTY = Squirt(0f, 0f, 0f, 0f)
    }

    data class All(val squirt: Float) : Squirt(squirt, squirt, squirt, squirt)
    data class Horizontal(val leftSquirt: Float, val rightSquirt: Float) : Squirt(leftSquirt, leftSquirt, rightSquirt, rightSquirt)
    data class Vertical(val topSquirt: Float, val bottomSquirt: Float) : Squirt(bottomSquirt, topSquirt, topSquirt, bottomSquirt)
}
package cicadas.mixtape.rendertest.objects

open class Radius(
    val leftBottomRadius: Float,
    val leftTopRadius: Float,
    val rightTopRadius: Float,
    val rightBottomRadius: Float
) {
    companion object {
        @JvmField
        val EMPTY = Radius(0f, 0f, 0f, 0f)
    }

    data class All(val radius: Float) : Radius(radius, radius, radius, radius)
    data class Horizontal(val leftRadius: Float, val rightRadius: Float) : Radius(leftRadius, leftRadius, rightRadius, rightRadius)
    data class Vertical(val topRadius: Float, val bottomRadius: Float) : Radius(bottomRadius, topRadius, topRadius, bottomRadius)
}
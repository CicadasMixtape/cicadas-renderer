package cicadas.mixtape.rendertest.animation.spring

import cicadas.mixtape.rendertest.animation.IAnimation
import cicadas.mixtape.rendertest.utility.math.DeltaTimer
import kotlin.Float

class SpringAnimation(
    val stiffness: Float,
    val damping: Float,
    val mass: Float = 1f,
    initialValue: Float = 0f
) : IAnimation {
    private var value = initialValue
    private var velocity = 0f
    private val timer = DeltaTimer()

    override fun update(newValue: Float): Float {
        val delta = timer.delta()
        val steps = (delta / 0.004f).toInt() + 1
        val h = delta / steps

        repeat(steps) {
            val spring = (newValue - value) * stiffness - velocity * damping
            velocity += (spring / mass) * h
            value += velocity * h
        }

        return value
    }

    override fun value() = value

    override fun reverse() = 1f - value

    override fun reset(newValue: Float) {
        value = newValue
    }
}
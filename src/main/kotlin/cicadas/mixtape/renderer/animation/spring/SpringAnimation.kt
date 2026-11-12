package cicadas.mixtape.renderer.animation.spring

import cicadas.mixtape.renderer.animation.IAnimation
import cicadas.mixtape.renderer.utility.time.DeltaTimer
import kotlin.Float

class SpringAnimation(
    private var stiffness: Float,
    private var damping: Float,
    private var mass: Float = 1f,
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

    fun stiffness(stiffness: Float): SpringAnimation {
        this.stiffness = stiffness
        return this
    }

    fun damping(damping: Float): SpringAnimation {
        this.damping = damping
        return this
    }

    fun mass(mass: Float): SpringAnimation {
        this.mass = mass
        return this
    }
}
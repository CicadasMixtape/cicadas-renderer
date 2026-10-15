package cicadas.mixtape.rendertest.animation.simple

import cicadas.mixtape.rendertest.animation.IAnimation
import cicadas.mixtape.rendertest.animation.easing.IEasing
import cicadas.mixtape.rendertest.api.toFloat
import cicadas.mixtape.rendertest.utility.math.Timer
import kotlin.time.Duration
import kotlin.time.DurationUnit

class SimpleAnimation(
    private val duration: Duration,
    private val easing: IEasing,
    initialValue: Float = 0f
) : IAnimation {
    private var value = initialValue
    private var fromValue = initialValue
    private var finishValue = initialValue
    private val timer = Timer()

    override fun update(newValue: Float): Float {
        if (newValue != finishValue) {
            fromValue = value
            finishValue = newValue
            timer.reset()
        }

        val progress = (timer.elapsed().toFloat() / duration.toFloat(DurationUnit.MILLISECONDS)).coerceIn(0f..1f)
        val ease = easing.update(progress)
        value = fromValue + (finishValue - fromValue) * ease
        return value
    }

    override fun value() = value

    override fun reverse() = 1f - value

    override fun reset(newValue: Float) {
        value = newValue
        fromValue = newValue
        finishValue = newValue
    }
}
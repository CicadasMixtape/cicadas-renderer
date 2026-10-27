package cicadas.mixtape.renderer.animation.easing.impl

import cicadas.mixtape.renderer.animation.easing.IEasing
import kotlin.math.pow

fun interface SimpleEasing : IEasing {
    companion object {
        @JvmField val LINEAR = SimpleEasing { it }
        @JvmField val EASE_OUT_BACK = SimpleEasing { 1f + 2.70158f * (it - 1f).pow(3f) + 1.70158f * (it - 1f).pow(2f) }
    }
}
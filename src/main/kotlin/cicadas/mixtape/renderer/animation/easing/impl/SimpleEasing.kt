package cicadas.mixtape.renderer.animation.easing.impl

import cicadas.mixtape.renderer.animation.easing.IEasing

fun interface SimpleEasing : IEasing {
    companion object {
        @JvmField val LINEAR = SimpleEasing { it }
    }
}
package cicadas.mixtape.rendertest.animation.easing.impl

import cicadas.mixtape.rendertest.animation.easing.IEasing
import kotlin.math.abs

fun interface BezierEasing : IEasing {
    companion object {
        fun create(x1: Float, y1: Float, x2: Float, y2: Float): BezierEasing {
            fun bez(a: Float, b: Float, s: Float): Float {
                val u = 1f - s
                return 3f * u * u * s * a + 3f * u * s * s * b + s * s * s
            }

            fun dBez(a: Float, b: Float, s: Float): Float {
                val u = 1f - s
                return 3f * u * u * a + 6f * u * s * (b - a) + 3f * s * s * (1f - b)
            }

            return BezierEasing {
                when {
                    it <= 0f -> 0f
                    it >= 1f -> 1f
                    else -> {
                        var s = it

                        run {
                            repeat(8) { _ ->
                                val b = bez(x1, x2, s) - it
                                val d = dBez(x1, x2, s)
                                if (abs(b) < 1e-5f) return@run
                                if (abs(d) < 1e-6f) return@run
                                s -= b / d
                            }
                        }

                        bez(y1, y2, s)
                    }
                }
            }
        }

        @JvmField val LINEAR = create(0f, 0f, 1f, 1f)
        @JvmField val EASE_OUT_BACK = create(0.34f, 1.56f, 0.64f, 1f)
    }
}
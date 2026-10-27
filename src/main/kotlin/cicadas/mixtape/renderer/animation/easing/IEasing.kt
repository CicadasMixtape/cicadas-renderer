package cicadas.mixtape.renderer.animation.easing

fun interface IEasing {
    fun update(f: Float): Float
}
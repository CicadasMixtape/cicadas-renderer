package cicadas.mixtape.renderer.utility.time

class DeltaTimer {
    private var last = System.nanoTime()

    fun delta(): Float {
        val now = System.nanoTime()
        val delta = ((now - last) / 1000000000f).coerceAtMost(0.05f)
        last = now
        return delta
    }
}
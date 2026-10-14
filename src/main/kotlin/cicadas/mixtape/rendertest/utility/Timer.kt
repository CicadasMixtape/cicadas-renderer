package cicadas.mixtape.rendertest.utility

import kotlin.time.Duration
import kotlin.time.DurationUnit

class Timer {
    var startTime = System.currentTimeMillis()

    fun reset() {
        startTime = System.currentTimeMillis()
    }

    fun passed(duration: Duration): Boolean {
        return elapsed() > duration.toLong(DurationUnit.MILLISECONDS)
    }

    fun elapsed() = System.currentTimeMillis() - startTime
}
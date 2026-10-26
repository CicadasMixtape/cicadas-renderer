package cicadas.mixtape.rendertest.events

import net.fabricmc.fabric.api.event.EventFactory

fun interface EventRender2D {
    companion object {
        @JvmField
        val EVENT= EventFactory.createArrayBacked(EventRender2D::class.java) { i ->
            EventRender2D {
                i.forEach { t -> t.invoke() }
            }
        }
    }

    fun invoke()
}
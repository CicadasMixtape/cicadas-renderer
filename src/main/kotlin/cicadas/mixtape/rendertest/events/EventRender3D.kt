package cicadas.mixtape.rendertest.events

import net.fabricmc.fabric.api.event.EventFactory

fun interface EventRender3D {
    companion object {
        @JvmField
        val EVENT= EventFactory.createArrayBacked(EventRender3D::class.java) { i ->
            EventRender3D {
                i.forEach { t -> t.invoke() }
            }
        }
    }

    fun invoke()
}
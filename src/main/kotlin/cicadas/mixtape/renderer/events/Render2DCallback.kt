package cicadas.mixtape.renderer.events

import cicadas.mixtape.renderer.context.UIContext
import net.fabricmc.fabric.api.event.EventFactory

fun interface Render2DCallback {
    companion object {
        @JvmField
        val EVENT = EventFactory.createArrayBacked(Render2DCallback::class.java) { callbacks ->
            Render2DCallback {
                callbacks.forEach { callback -> callback.invoke(it) }
            }
        }
    }

    fun invoke(context: UIContext)
}
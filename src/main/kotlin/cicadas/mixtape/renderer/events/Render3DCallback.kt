package cicadas.mixtape.renderer.events

import com.mojang.blaze3d.vertex.PoseStack
import net.fabricmc.fabric.api.event.EventFactory

fun interface Render3DCallback {
    companion object {
        @JvmField
        val EVENT = EventFactory.createArrayBacked(Render3DCallback::class.java) { callbacks ->
            Render3DCallback {
                callbacks.forEach { callback -> callback.invoke(it) }
            }
        }
    }

    fun invoke(stack: PoseStack)
}
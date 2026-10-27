package cicadas.mixtape.rendertest.events

import com.mojang.blaze3d.vertex.PoseStack
import net.fabricmc.fabric.api.event.EventFactory

fun interface EventRender3D {
    companion object {
        @JvmField
        val EVENT= EventFactory.createArrayBacked(EventRender3D::class.java) { events ->
            EventRender3D {
                events.forEach { event -> event.invoke(it) }
            }
        }
    }

    fun invoke(stack: PoseStack)
}
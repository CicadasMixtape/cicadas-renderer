package cicadas.mixtape.rendertest.events

import com.mojang.blaze3d.vertex.PoseStack
import net.fabricmc.fabric.api.event.EventFactory

fun interface EventRender2D {
    companion object {
        @JvmField
        val EVENT= EventFactory.createArrayBacked(EventRender2D::class.java) { events ->
            EventRender2D {
                events.forEach { event -> event.invoke(it) }
            }
        }
    }

    fun invoke(stack: PoseStack)
}
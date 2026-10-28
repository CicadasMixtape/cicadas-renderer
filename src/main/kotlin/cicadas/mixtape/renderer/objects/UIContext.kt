package cicadas.mixtape.renderer.objects

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.MouseHandler

class UIContext private constructor(
    val stack: PoseStack,
    val mouseX: Float,
    val mouseY: Float
) {
    companion object {
        @JvmStatic
        fun create(stack: PoseStack, handler: MouseHandler) = UIContext(stack, handler.xpos().toFloat() / 2f, handler.ypos().toFloat() / 2f)
    }
}
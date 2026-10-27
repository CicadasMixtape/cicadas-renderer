package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.api.createBuffer
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import kotlin.use

object KawaseUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .putVec2()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer("Kawase", SIZE)

    fun update(offset: Float, halfPixel: Vector2f): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putFloat(offset)
                .putVec2(halfPixel)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Kawase", BUFFER.slice())
    }
}
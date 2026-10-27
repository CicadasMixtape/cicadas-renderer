package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.api.createBuffer
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Vector4f
import org.lwjgl.system.MemoryStack
import kotlin.use

object OutlineUniform {
    private val SIZE = Std140SizeCalculator()
        .putVec4()
        .putFloat()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer("Outline", SIZE)

    fun update(tint: Vector4f, lineWidth: Float): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putVec4(tint)
                .putFloat(lineWidth)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Outline", BUFFER.slice())
    }
}
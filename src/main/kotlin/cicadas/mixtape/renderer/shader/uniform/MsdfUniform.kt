package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.api.createBuffer
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.lwjgl.system.MemoryStack
import kotlin.use

object MsdfUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .putFloat()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer("Msdf", SIZE)

    fun update(range: Float, thickness: Float): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putFloat(range)
                .putFloat(thickness)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Msdf", BUFFER.slice())
    }
}
package cicadas.mixtape.rendertest.shader.uniform

import cicadas.mixtape.rendertest.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.lwjgl.system.MemoryStack

object SquircleUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "SquircleBuffer" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(squirt: Float): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putFloat(squirt)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Squircle", BUFFER.slice())
    }
}
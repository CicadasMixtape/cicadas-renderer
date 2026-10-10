package cicadas.mixtape.rendertest.shader.uniform

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.lwjgl.system.MemoryStack
import kotlin.use

object FontUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .putFloat()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "FontData" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(range: Float, thickness: Float): GpuBufferSlice {
        MemoryStack.stackPush().use {
            val data = Std140Builder.onStack(it, SIZE)
                .putFloat(range)
                .putFloat(thickness)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), data)
        }

        return BUFFER.slice()
    }
}
package cicadas.mixtape.rendertest.shader.uniform

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import kotlin.use

object BlurUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .putVec2()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "BlurData" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(offset: Float, halfPixel: Vector2f): GpuBufferSlice {
        MemoryStack.stackPush().use {
            val data = Std140Builder.onStack(it, SIZE)
                .putFloat(offset)
                .putVec2(halfPixel)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), data)
        }

        return BUFFER.slice()
    }
}
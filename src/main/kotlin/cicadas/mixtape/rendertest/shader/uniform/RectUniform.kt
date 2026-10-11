package cicadas.mixtape.rendertest.shader.uniform

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack

object RectUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .putVec2()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "RectData" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(radius: Float, vector2f: Vector2f): GpuBufferSlice {
        MemoryStack.stackPush().use {
            val data = Std140Builder.onStack(it, SIZE)
                .putFloat(radius)
                .putVec2(vector2f)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), data)
        }

        return BUFFER.slice()
    }
}
package cicadas.mixtape.rendertest.shader.uniform

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
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

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "OutlineBuffer" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(tint: Vector4f, lineWidth: Float): GpuBufferSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putVec4(tint)
                .putFloat(lineWidth)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return BUFFER.slice()
    }
}
package cicadas.mixtape.rendertest.shader.uniform

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import kotlin.use

object ScreenUniform {
    private val SIZE = Std140SizeCalculator()
        .putVec2()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer(
        { "ScreenBuffer" },
        GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST,
        SIZE.toLong()
    )

    fun update(screenSize: Vector2f): GpuBufferSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putVec2(screenSize)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return BUFFER.slice()
    }


}
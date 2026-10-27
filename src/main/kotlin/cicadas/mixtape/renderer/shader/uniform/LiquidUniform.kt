package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.api.createBuffer
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.lwjgl.system.MemoryStack
import kotlin.use

object LiquidUniform {
    private val SIZE = Std140SizeCalculator()
        .putFloat()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer("Liquid", SIZE)

    fun update(power: Float): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putFloat(power)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Liquid", BUFFER.slice())
    }
}
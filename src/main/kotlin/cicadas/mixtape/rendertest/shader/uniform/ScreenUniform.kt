package cicadas.mixtape.rendertest.shader.uniform

import cicadas.mixtape.rendertest.api.createBuffer
import cicadas.mixtape.rendertest.shader.slice.CustomSlice
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

    private val BUFFER = RenderSystem.getDevice().createBuffer("Screen", SIZE)

    fun update(screenSize: Vector2f): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putVec2(screenSize)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Screen", BUFFER.slice())
    }
}
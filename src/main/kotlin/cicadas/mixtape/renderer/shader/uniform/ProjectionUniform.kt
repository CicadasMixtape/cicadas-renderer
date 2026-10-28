package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.api.createBuffer
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Matrix4f
import org.lwjgl.system.MemoryStack
import kotlin.use

object ProjectionUniform {
    private val SIZE = Std140SizeCalculator()
        .putMat4f()
        .putMat4f()
        .get()

    private val BUFFER = RenderSystem.getDevice().createBuffer("Projection", SIZE)

    fun update(projection: Matrix4f, modelView: Matrix4f): CustomSlice {
        MemoryStack.stackPush().use {
            val buffer = Std140Builder.onStack(it, SIZE)
                .putMat4f(projection)
                .putMat4f(modelView)
                .get()

            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), buffer)
        }

        return CustomSlice("Projection", BUFFER.slice())
    }
}
package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicUniformStorage
import org.joml.Matrix4f
import java.nio.ByteBuffer

data class ProjectionData(val projection: Matrix4f, val modelView: Matrix4f) : DynamicUniformStorage.DynamicUniform {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putMat4f(projection).putMat4f(modelView)
    }
}
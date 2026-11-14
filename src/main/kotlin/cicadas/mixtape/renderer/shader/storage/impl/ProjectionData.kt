package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicGpuDataStorage
import org.joml.Matrix4f
import java.nio.ByteBuffer

data class ProjectionData(val proj: Matrix4f, val modelView: Matrix4f) : DynamicGpuDataStorage.DynamicGpuData{
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putMat4f(proj).putMat4f(modelView)
    }
}
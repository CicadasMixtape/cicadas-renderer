package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicUniformStorage
import java.nio.ByteBuffer

data class MsdfData(val range: Float, val thickness: Float) : DynamicUniformStorage.DynamicUniform {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putFloat(range).putFloat(thickness)
    }
}
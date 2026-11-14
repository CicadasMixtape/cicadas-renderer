package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicGpuDataStorage
import org.joml.Vector2f
import java.nio.ByteBuffer

data class KawaseData(val offset: Float, val halfPixel: Vector2f) : DynamicGpuDataStorage.DynamicGpuData {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putFloat(offset).putVec2(halfPixel)
    }
}
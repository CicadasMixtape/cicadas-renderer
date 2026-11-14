package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicGpuDataStorage
import java.nio.ByteBuffer

data class LiquidData(val power: Float) : DynamicGpuDataStorage.DynamicGpuData {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putFloat(power)
    }
}
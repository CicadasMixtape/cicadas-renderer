package cicadas.mixtape.renderer.testmod.shader.data

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicUniformStorage
import org.joml.Vector4f
import java.nio.ByteBuffer

data class OutlineData(val color: Vector4f, val lineWidth: Float) : DynamicUniformStorage.DynamicUniform {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putVec4(color).putFloat(lineWidth)
    }
}
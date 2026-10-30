package cicadas.mixtape.renderer.shader.storage.impl

import com.mojang.blaze3d.buffers.Std140Builder
import net.minecraft.client.renderer.DynamicUniformStorage
import org.joml.Vector2f
import java.nio.ByteBuffer

data class ScreenData(val screenSize: Vector2f) : DynamicUniformStorage.DynamicUniform {
    override fun write(buffer: ByteBuffer) {
        Std140Builder.intoBuffer(buffer).putVec2(screenSize)
    }
}
package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.KawaseData
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import org.joml.Vector2f

object KawaseUniform {
    private val SIZE = Std140SizeCalculator().putFloat().putVec2().get()
    private val STORAGE = UniformStorage<KawaseData>("Kawase", SIZE)

    fun update(offset: Float, halfPixel: Vector2f) = STORAGE.write(KawaseData(offset, halfPixel))
}
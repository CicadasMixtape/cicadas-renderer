package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.MsdfData
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object MsdfUniform {
    private val SIZE = Std140SizeCalculator().putFloat().putFloat().get()
    private val STORAGE = UniformStorage<MsdfData>("Msdf", SIZE)

    fun update(range: Float, thickness: Float) = STORAGE.write(MsdfData(range, thickness))
}
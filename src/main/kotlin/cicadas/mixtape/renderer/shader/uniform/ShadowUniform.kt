package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.ShadowData
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object ShadowUniform {
    private val SIZE = Std140SizeCalculator().putFloat().get()
    private val STORAGE = UniformStorage<ShadowData>("Shadow", SIZE)

    fun update(power: Float) = STORAGE.write(ShadowData(power))
}
package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.LiquidData
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object LiquidUniform {
    private val SIZE = Std140SizeCalculator().putFloat().get()
    private val STORAGE = UniformStorage<LiquidData>("Liquid", SIZE)

    fun update(power: Float) = STORAGE.write(LiquidData(power))
}
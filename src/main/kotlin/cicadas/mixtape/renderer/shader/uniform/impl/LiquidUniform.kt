package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.LiquidData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object LiquidUniform : CustomUniform<LiquidData>(
    "Liquid",
    Std140SizeCalculator().putFloat().get()
)
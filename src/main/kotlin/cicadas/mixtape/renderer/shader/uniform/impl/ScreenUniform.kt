package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.ScreenData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object ScreenUniform : CustomUniform<ScreenData>(
    "Screen",
    Std140SizeCalculator().putVec2().get()
)
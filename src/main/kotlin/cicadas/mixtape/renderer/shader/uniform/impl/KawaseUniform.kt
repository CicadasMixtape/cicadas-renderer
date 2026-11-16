package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.KawaseData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object KawaseUniform : CustomUniform<KawaseData>(
    "Kawase",
    Std140SizeCalculator().putFloat().putVec2().get()
)
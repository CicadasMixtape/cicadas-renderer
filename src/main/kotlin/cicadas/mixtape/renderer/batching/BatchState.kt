package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.pipeline.RenderPipeline

data class BatchState(
    val pipeline: RenderPipeline,
    val samplers: Set<CustomSampler>,
    val slices: Set<CustomSlice>
)
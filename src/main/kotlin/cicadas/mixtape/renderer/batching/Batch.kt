package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.shader.builder.CustomBuilder

data class Batch(
    val batched: Batched,
    val builder: CustomBuilder,
    val start: Int,
    var count: Int
) {
    fun match(batched: Batched) = this.batched.pipeline == batched.pipeline && this.batched.samplers == batched.samplers && this.batched.slices == batched.slices
}
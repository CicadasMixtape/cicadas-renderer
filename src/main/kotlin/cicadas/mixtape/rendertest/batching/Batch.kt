package cicadas.mixtape.rendertest.batching

import cicadas.mixtape.rendertest.shader.builder.CustomBuilder

data class Batch(
    val batched: Batched,
    val builder: CustomBuilder,
    val start: Int,
    var count: Int
) {
    fun match(batched: Batched) = this.batched.pipeline == batched.pipeline && this.batched.samplers == batched.samplers && this.batched.slices == batched.slices
}
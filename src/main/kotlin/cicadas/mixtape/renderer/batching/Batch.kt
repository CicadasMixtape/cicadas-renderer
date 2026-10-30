package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.shader.builder.CustomBuilder

data class Batch(
    val batched: Batched,
    val builder: CustomBuilder,
    val start: Int,
    var count: Int
) {
    fun match(batched: Batched, builder: CustomBuilder) = this.batched.state == batched.state && this.builder == builder
}
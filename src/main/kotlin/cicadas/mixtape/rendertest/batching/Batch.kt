package cicadas.mixtape.rendertest.batching

import cicadas.mixtape.rendertest.shader.builder.CustomBuilder

data class Batch(
    val batched: Batched,
    val builder: CustomBuilder,
    val start: Int,
    val count: Int
)
package cicadas.mixtape.renderer.batching

data class Batch(
    val batched: Batched,
    val builder: BatchBuilder,
    val start: Int,
    var count: Int
) {
    fun match(batched: Batched, builder: BatchBuilder) = this.batched.state == batched.state && this.builder === builder
}
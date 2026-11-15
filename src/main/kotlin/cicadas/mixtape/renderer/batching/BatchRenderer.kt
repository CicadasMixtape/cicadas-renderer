package cicadas.mixtape.renderer.batching

abstract class BatchRenderer {
    protected val batching = Batching()

    fun update() = batching.update()
}
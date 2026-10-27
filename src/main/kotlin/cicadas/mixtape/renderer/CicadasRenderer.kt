package cicadas.mixtape.renderer

import cicadas.mixtape.renderer.api.init
import cicadas.mixtape.renderer.shader.Formats
import cicadas.mixtape.renderer.shader.Types
import cicadas.mixtape.renderer.shader.Pipelines
import cicadas.mixtape.renderer.shader.Snippets
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier

object CicadasRenderer : ModInitializer {
    override fun onInitialize() {
        Formats.init()
        Snippets.init()
        Pipelines.init()
        Types.init()
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("cicadasrenderer", string)
}
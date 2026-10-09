package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.init
import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.Snippets
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier

object RenderTest : ModInitializer {
    override fun onInitialize() {
        Formats.init()
        Snippets.init()
        Pipelines.init()
        RenderManager.init()
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("rendertest", string)
}
package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.init
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier

object RenderTest : ModInitializer {
    override fun onInitialize() {
        Pipelines.init()
        RenderManager.init()
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("rendertest", string)
}
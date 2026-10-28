package cicadas.mixtape.renderer

import net.minecraft.resources.Identifier

object CicadasRenderer {
    fun id(string: String) = Identifier.fromNamespaceAndPath("cicadasrenderer", string)
}
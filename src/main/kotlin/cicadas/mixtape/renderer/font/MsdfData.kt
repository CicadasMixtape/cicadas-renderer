package cicadas.mixtape.renderer.font

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MsdfData(
    @SerialName("atlas") val atlas: AtlasData,
    @SerialName("metrics") val metrics: MetricsData,
    @SerialName("glyphs") val glyphs: ArrayList<GlyphData>,
) {
    @Serializable
    data class AtlasData(
        @SerialName("distanceRange") val distanceRange: Float,
        @SerialName("width") val width: Float,
        @SerialName("height") val height: Float
    )

    @Serializable
    data class MetricsData(
        @SerialName("lineHeight") val lineHeight: Float,
        @SerialName("ascender") val ascender: Float,
        @SerialName("descender") val descender: Float
    )

    @Serializable
    data class GlyphData(
        @SerialName("unicode") val unicode: Int,
        @SerialName("advance") val advance: Float,
        @SerialName("planeBounds") val planeBounds: BoundsData? = null,
        @SerialName("atlasBounds") val atlasBounds: BoundsData? = null
    )

    @Serializable
    data class BoundsData(
        @SerialName("left") val left: Float,
        @SerialName("top") val top: Float,
        @SerialName("right") val right: Float,
        @SerialName("bottom") val bottom: Float
    )
}
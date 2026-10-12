package cicadas.mixtape.rendertest.utility

import cicadas.mixtape.rendertest.api.addLine
import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.api.withCamera
import cicadas.mixtape.rendertest.shader.Types
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.world.phys.AABB
import org.joml.Matrix4f
import java.awt.Color

object Renderer3D {
    fun drawBox(aabb: AABB, matrix4f: Matrix4f, color: Color) {
        val aabb = aabb.withCamera
        val builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.minZ).setColor(color.rgb)

        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.minZ).setColor(color.rgb)

        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.minZ).setColor(color.rgb)

        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.maxZ).setColor(color.rgb)

        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.minY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.minY, aabb.maxZ).setColor(color.rgb)

        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.minZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.maxX, aabb.maxY, aabb.maxZ).setColor(color.rgb)
        builder.addVertex(matrix4f, aabb.minX, aabb.maxY, aabb.maxZ).setColor(color.rgb)

        Types.quads().draw(builder.buildOrThrow())
    }

    fun drawOutline(aabb: AABB, matrix4f: Matrix4f, width: Float, color: Color) {
        val aabb = aabb.withCamera
        val builder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)

        builder.addLine(matrix4f, aabb.minX, aabb.minY, aabb.minZ, aabb.minX, aabb.maxY, aabb.minZ, width, color.rgb)
        builder.addLine(matrix4f, aabb.maxX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.minZ, width, color.rgb)
        builder.addLine(matrix4f, aabb.maxX, aabb.minY, aabb.maxZ, aabb.maxX, aabb.maxY, aabb.maxZ, width, color.rgb)
        builder.addLine(matrix4f, aabb.minX, aabb.minY, aabb.maxZ, aabb.minX, aabb.maxY, aabb.maxZ, width, color.rgb)

        for (y in doubleArrayOf(aabb.minY, aabb.maxY)) {
            builder.addLine(matrix4f, aabb.minX, y, aabb.minZ, aabb.maxX, y, aabb.minZ, width, color.rgb)
            builder.addLine(matrix4f, aabb.maxX, y, aabb.minZ, aabb.maxX, y, aabb.maxZ, width, color.rgb)
            builder.addLine(matrix4f, aabb.maxX, y, aabb.maxZ, aabb.minX, y, aabb.maxZ, width, color.rgb)
            builder.addLine(matrix4f, aabb.minX, y, aabb.maxZ, aabb.minX, y, aabb.minZ, width, color.rgb)
        }

        Types.lines().draw(builder.buildOrThrow())
    }
}
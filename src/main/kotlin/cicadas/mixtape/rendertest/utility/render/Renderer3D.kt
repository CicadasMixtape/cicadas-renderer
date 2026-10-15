package cicadas.mixtape.rendertest.utility.render

import cicadas.mixtape.rendertest.api.aabb
import cicadas.mixtape.rendertest.api.addLine
import cicadas.mixtape.rendertest.api.beginLines
import cicadas.mixtape.rendertest.api.beginQuads
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.withCamera
import cicadas.mixtape.rendertest.interfaces.ILevelRenderer
import cicadas.mixtape.rendertest.objects.DefaultBox
import cicadas.mixtape.rendertest.objects.DefaultOutline
import cicadas.mixtape.rendertest.objects.VoxelBox
import cicadas.mixtape.rendertest.objects.VoxelOutline
import cicadas.mixtape.rendertest.shader.Types
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import org.joml.Matrix4f

object Renderer3D {
    private val defaultBoxes = arrayListOf<DefaultBox>()
    private val defaultOutlines = arrayListOf<DefaultOutline>()
    private val voxelBoxes = arrayListOf<VoxelBox>()
    private val voxelOutlines = arrayListOf<VoxelOutline>()
    private val frustum get() = (mc.levelRenderer as ILevelRenderer).`rendertest$getFrustum`()

    fun addDefaultBox(box: DefaultBox) {
        if (frustum?.isVisible(box.aabb) == false) {
            return
        }

        defaultBoxes.add(box)
    }

    fun addDefaultOutline(outline: DefaultOutline) {
        if (frustum?.isVisible(outline.aabb) == false) {
            return
        }

        defaultOutlines.add(outline)
    }

    fun addVoxelBox(box: VoxelBox) {
        if (frustum?.isVisible(box.pos.aabb) == false) {
            return
        }

        if (box.shape.isEmpty) {
            return
        }

        voxelBoxes.add(box)
    }

    fun addVoxelOutline(outline: VoxelOutline) {
        if (frustum?.isVisible(outline.pos.aabb) == false) {
            return
        }

        if (outline.shape.isEmpty) {
            return
        }

        voxelOutlines.add(outline)
    }

    private fun drawDefaultBox(builder: BufferBuilder, matrix4f: Matrix4f, box: DefaultBox) {
        val aabb = box.aabb.withCamera
        val color = box.color
        val x1 = aabb.minX.toFloat()
        val y1 = aabb.minY.toFloat()
        val z1 = aabb.minZ.toFloat()
        val x2 = aabb.maxX.toFloat()
        val y2 = aabb.maxY.toFloat()
        val z2 = aabb.maxZ.toFloat()

        builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)

        builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)

        builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)

        builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)

        builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)

        builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)
        builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
        builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)
    }

    private fun drawDefaultOutline(builder: BufferBuilder, matrix4f: Matrix4f, outline: DefaultOutline) {
        val aabb = outline.aabb.withCamera
        val color = outline.color
        val lineWidth = outline.lineWidth
        val x1 = aabb.minX.toFloat()
        val y1 = aabb.minY.toFloat()
        val z1 = aabb.minZ.toFloat()
        val x2 = aabb.maxX.toFloat()
        val y2 = aabb.maxY.toFloat()
        val z2 = aabb.maxZ.toFloat()

        builder.addLine(matrix4f, x1, y1, z1, x1, y2, z1, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y1, z1, x2, y2, z1, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y1, z2, x2, y2, z2, color.rgb, lineWidth)
        builder.addLine(matrix4f, x1, y1, z2, x1, y2, z2, color.rgb, lineWidth)

        builder.addLine(matrix4f, x1, y1, z1, x2, y1, z1, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y1, z1, x2, y1, z2, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y1, z2, x1, y1, z2, color.rgb, lineWidth)
        builder.addLine(matrix4f, x1, y1, z2, x1, y1, z1, color.rgb, lineWidth)

        builder.addLine(matrix4f, x1, y2, z1, x2, y2, z1, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y2, z1, x2, y2, z2, color.rgb, lineWidth)
        builder.addLine(matrix4f, x2, y2, z2, x1, y2, z2, color.rgb, lineWidth)
        builder.addLine(matrix4f, x1, y2, z2, x1, y2, z1, color.rgb, lineWidth)
    }

    private fun drawVoxelBox(builder: BufferBuilder, matrix4f: Matrix4f, box: VoxelBox) {
        val shape = box.shape
        val pos = box.pos
        val color = box.color
        val camera = mc.gameRenderer.mainCamera.position()

        shape.forAllBoxes { x1, y1, z1, x2, y2, z2 ->
            val x1 = (x1 + (pos.x - camera.x())).toFloat()
            val y1 = (y1 + (pos.y - camera.y())).toFloat()
            val z1 = (z1 + (pos.z - camera.z())).toFloat()
            val x2 = (x2 + (pos.x - camera.x())).toFloat()
            val y2 = (y2 + (pos.y - camera.y())).toFloat()
            val z2 = (z2 + (pos.z - camera.z())).toFloat()

            builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)

            builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)

            builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)

            builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)

            builder.addVertex(matrix4f, x1, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y1, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y1, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y1, z2).setColor(color.rgb)

            builder.addVertex(matrix4f, x1, y2, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z1).setColor(color.rgb)
            builder.addVertex(matrix4f, x2, y2, z2).setColor(color.rgb)
            builder.addVertex(matrix4f, x1, y2, z2).setColor(color.rgb)
        }
    }

    private fun drawVoxelOutline(builder: BufferBuilder, matrix4f: Matrix4f, outline: VoxelOutline) {
        val shape = outline.shape
        val pos = outline.pos
        val color = outline.color
        val lineWidth = outline.lineWidth
        val camera = mc.gameRenderer.mainCamera.position()

        shape.forAllEdges { x1, y1, z1, x2, y2, z2 ->
            val x1 = (x1 + (pos.x - camera.x())).toFloat()
            val y1 = (y1 + (pos.y - camera.y())).toFloat()
            val z1 = (z1 + (pos.z - camera.z())).toFloat()
            val x2 = (x2 + (pos.x - camera.x())).toFloat()
            val y2 = (y2 + (pos.y - camera.y())).toFloat()
            val z2 = (z2 + (pos.z - camera.z())).toFloat()
            builder.addLine(matrix4f, x1, y1, z1, x2, y2, z2, color.rgb, lineWidth)
        }
    }

    fun drawDefaults(matrix4f: Matrix4f) {
        if (defaultBoxes.isNotEmpty()) {
            val builder = Tesselator.getInstance().beginQuads(DefaultVertexFormat.POSITION_COLOR)

            defaultBoxes.forEach {
                drawDefaultBox(builder, matrix4f, it)
            }

            Types.quads().draw(builder.buildOrThrow())
        }

        if (defaultOutlines.isNotEmpty()) {
            val builder = Tesselator.getInstance().beginLines(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)

            defaultOutlines.forEach {
                drawDefaultOutline(builder, matrix4f, it)
            }

            Types.lines().draw(builder.buildOrThrow())
        }
    }

    fun drawVoxels(matrix4f: Matrix4f) {
        if (voxelBoxes.isNotEmpty()) {
            val builder = Tesselator.getInstance().beginQuads(DefaultVertexFormat.POSITION_COLOR)

            voxelBoxes.forEach {
                drawVoxelBox(builder, matrix4f, it)
            }

            Types.quads().draw(builder.buildOrThrow())
        }

        if (voxelOutlines.isNotEmpty()) {
            val builder = Tesselator.getInstance().beginLines(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)

            voxelOutlines.forEach {
                drawVoxelOutline(builder, matrix4f, it)
            }

            Types.lines().draw(builder.buildOrThrow())
        }
    }

    fun clearAll() {
        defaultBoxes.clear()
        defaultOutlines.clear()
        voxelBoxes.clear()
        voxelOutlines.clear()
    }
}
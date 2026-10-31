package cicadas.mixtape.renderer.utility.render

import cicadas.mixtape.renderer.api.addLine
import cicadas.mixtape.renderer.api.frustum
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.api.withCamera
import cicadas.mixtape.renderer.context.LevelContext
import cicadas.mixtape.renderer.objects.DefaultBox
import cicadas.mixtape.renderer.objects.DefaultOutline
import cicadas.mixtape.renderer.objects.VoxelBox
import cicadas.mixtape.renderer.objects.VoxelOutline
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.builder.CustomBuffer
import cicadas.mixtape.renderer.shader.uniform.ScreenUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.world.phys.AABB
import org.joml.Matrix4f
import java.util.OptionalDouble
import java.util.OptionalInt

object Renderer3D {
    private val defaultBoxes = arrayListOf<DefaultBox>()
    private val defaultOutlines = arrayListOf<DefaultOutline>()
    private val voxelBoxes = arrayListOf<VoxelBox>()
    private val voxelOutlines = arrayListOf<VoxelOutline>()

    fun addDefaultBox(box: DefaultBox) {
        if (!frustum.isVisible(box.aabb)) {
            return
        }

        defaultBoxes.add(box)
    }

    fun addDefaultOutline(outline: DefaultOutline) {
        if (!frustum.isVisible(outline.aabb)) {
            return
        }

        defaultOutlines.add(outline)
    }

    fun addVoxelBox(box: VoxelBox) {
        if (!frustum.isVisible(AABB(box.pos))) {
            return
        }

        if (box.shape.isEmpty) {
            return
        }

        voxelBoxes.add(box)
    }

    fun addVoxelOutline(outline: VoxelOutline) {
        if (!frustum.isVisible(AABB(outline.pos))) {
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

    private fun drawBoxes(context: LevelContext) {
        if (defaultBoxes.isNotEmpty() || voxelBoxes.isNotEmpty()) {
            val builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

            defaultBoxes.forEach { drawDefaultBox(builder, context.stack.last().pose(), it) }
            voxelBoxes.forEach { drawVoxelBox(builder, context.stack.last().pose(), it) }

            val mesh = builder.buildOrThrow()
            val buffer = CustomBuffer.create(mesh)
            val projectionUniform = context.projection.uniform()

            RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                { "cicadasrenderer-boxes-pass" },
                mainTarget.colorTextureView!!, OptionalInt.empty(),
                mainTarget.depthTextureView, OptionalDouble.empty()
            ).use {
                it.setPipeline(CicadasPipelines.POSITION_COLOR_PIPELINE)
                it.setUniform(projectionUniform)

                buffer.setBuffer(it)
                buffer.drawIndexed(it)
            }

            mesh.close()
        }
    }

    private fun drawOutlines(context: LevelContext) {
        if (defaultOutlines.isNotEmpty() || voxelOutlines.isNotEmpty()) {
            val builder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)

            defaultOutlines.forEach { drawDefaultOutline(builder, context.stack.last().pose(), it) }
            voxelOutlines.forEach { drawVoxelOutline(builder, context.stack.last().pose(), it) }

            val mesh = builder.buildOrThrow()
            val buffer = CustomBuffer.create(mesh)
            val projectionUniform = context.projection.uniform()
            val screenUniform = ScreenUniform.update(mainTarget.screenSize)

            RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                { "cicadasrenderer-lines-pass" },
                mainTarget.colorTextureView!!, OptionalInt.empty(),
                mainTarget.depthTextureView, OptionalDouble.empty()
            ).use {
                it.setPipeline(CicadasPipelines.LINES_PIPELINE)
                it.setUniform(projectionUniform)
                it.setUniform(screenUniform)

                buffer.setBuffer(it)
                buffer.drawIndexed(it)
            }

            mesh.close()
        }
    }

    fun update(context: LevelContext) {
        drawBoxes(context)
        drawOutlines(context)

        defaultBoxes.clear()
        defaultOutlines.clear()
        voxelBoxes.clear()
        voxelOutlines.clear()
    }
}
package cicadas.mixtape.renderer.utility.render

import cicadas.mixtape.renderer.api.addLine
import cicadas.mixtape.renderer.api.frustum
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.api.withCamera
import cicadas.mixtape.renderer.objects.DefaultBox
import cicadas.mixtape.renderer.objects.DefaultOutline
import cicadas.mixtape.renderer.objects.VoxelBox
import cicadas.mixtape.renderer.objects.VoxelOutline
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.builder.CustomBuffer
import cicadas.mixtape.renderer.shader.uniform.ProjectionUniform
import cicadas.mixtape.renderer.shader.uniform.ScreenUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.world.phys.AABB
import org.joml.Matrix4f
import java.util.OptionalDouble
import java.util.OptionalInt

object Renderer3D {
    private var stack = PoseStack()
    private var projection = Matrix4f()
    private var modelView = Matrix4f()

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

    private fun drawDefaultBox(builder: BufferBuilder, box: DefaultBox) {
        val aabb = box.aabb.withCamera
        val color = box.color
        val x1 = aabb.minX.toFloat()
        val y1 = aabb.minY.toFloat()
        val z1 = aabb.minZ.toFloat()
        val x2 = aabb.maxX.toFloat()
        val y2 = aabb.maxY.toFloat()
        val z2 = aabb.maxZ.toFloat()

        builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)

        builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)

        builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)

        builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)

        builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)

        builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
        builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)
    }

    private fun drawDefaultOutline(builder: BufferBuilder, outline: DefaultOutline) {
        val aabb = outline.aabb.withCamera
        val color = outline.color
        val lineWidth = outline.lineWidth
        val x1 = aabb.minX.toFloat()
        val y1 = aabb.minY.toFloat()
        val z1 = aabb.minZ.toFloat()
        val x2 = aabb.maxX.toFloat()
        val y2 = aabb.maxY.toFloat()
        val z2 = aabb.maxZ.toFloat()

        builder.addLine(stack.last().pose(), x1, y1, z1, x1, y2, z1, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y1, z1, x2, y2, z1, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y1, z2, x2, y2, z2, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x1, y1, z2, x1, y2, z2, color.rgb, lineWidth)

        builder.addLine(stack.last().pose(), x1, y1, z1, x2, y1, z1, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y1, z1, x2, y1, z2, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y1, z2, x1, y1, z2, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x1, y1, z2, x1, y1, z1, color.rgb, lineWidth)

        builder.addLine(stack.last().pose(), x1, y2, z1, x2, y2, z1, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y2, z1, x2, y2, z2, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x2, y2, z2, x1, y2, z2, color.rgb, lineWidth)
        builder.addLine(stack.last().pose(), x1, y2, z2, x1, y2, z1, color.rgb, lineWidth)
    }

    private fun drawVoxelBox(builder: BufferBuilder, box: VoxelBox) {
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

            builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)

            builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)

            builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)

            builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)

            builder.addVertex(stack.last().pose(), x1, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y1, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y1, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y1, z2).setColor(color.rgb)

            builder.addVertex(stack.last().pose(), x1, y2, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z1).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x2, y2, z2).setColor(color.rgb)
            builder.addVertex(stack.last().pose(), x1, y2, z2).setColor(color.rgb)
        }
    }

    private fun drawVoxelOutline(builder: BufferBuilder, outline: VoxelOutline) {
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
            builder.addLine(stack.last().pose(), x1, y1, z1, x2, y2, z2, color.rgb, lineWidth)
        }
    }

    fun drawBoxes() {
        if (defaultBoxes.isNotEmpty() || voxelBoxes.isNotEmpty()) {
            val builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

            if (defaultBoxes.isNotEmpty()) {
                defaultBoxes.forEach {
                    drawDefaultBox(builder, it)
                }
            }

            if (voxelBoxes.isNotEmpty()) {
                voxelBoxes.forEach {
                    drawVoxelBox(builder, it)
                }
            }

            val mesh = builder.buildOrThrow()
            val buffer = CustomBuffer.create(mesh)
            val projectionUniform = ProjectionUniform.update(projection, modelView)

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

    fun drawOutlines() {
        if (defaultOutlines.isNotEmpty() || voxelOutlines.isNotEmpty()) {
            val builder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)

            if (defaultOutlines.isNotEmpty()) {
                defaultOutlines.forEach {
                    drawDefaultOutline(builder, it)
                }
            }

            if (voxelOutlines.isNotEmpty()) {
                voxelOutlines.forEach {
                    drawVoxelOutline(builder, it)
                }
            }

            val mesh = builder.buildOrThrow()
            val buffer = CustomBuffer.create(mesh)
            val projectionUniform = ProjectionUniform.update(projection, modelView)
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

    fun clearAll() {
        defaultBoxes.clear()
        defaultOutlines.clear()
        voxelBoxes.clear()
        voxelOutlines.clear()
    }

    fun stack(stack: PoseStack) {
        this.stack = stack
    }

    fun projection(projection: Matrix4f) {
        this.projection = projection
    }
}
#version 330
#extension GL_ARB_separate_shader_objects : require

#include <cicadasrenderer:projection_uniform.glsl>
#include <cicadasrenderer:vertices.glsl>

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 Size;
layout(location = 2) in float Radius;
layout(location = 3) in vec4 Color;

layout(location = 0) out vec2 vertexSize;
layout(location = 1) out vec2 vertexCoord;
layout(location = 2) out float vertexRadius;
layout(location = 3) out vec4 vertexColor;

void main() {
    gl_Position = proj * modelView * vec4(Position, 1.0);
    vertexSize = Size;
    vertexCoord = rvertexcoord(gl_VertexIndex);
    vertexRadius = Radius;
    vertexColor = Color;
}
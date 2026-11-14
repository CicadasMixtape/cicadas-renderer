#version 330

#moj_import <cicadasrenderer:projection_uniform.glsl>
#moj_import <cicadasrenderer:vertices.glsl>

in vec3 Position;
in vec2 Size;
in float Radius;
in float Squirt;
in vec4 Color;

out vec2 vertexSize;
out vec2 vertexCoord;
out float vertexRadius;
out float vertexSquirt;
out vec4 vertexColor;

void main() {
    gl_Position = proj * modelView * vec4(Position, 1.0);
    vertexSize = Size;
    vertexCoord = rvertexcoord(gl_VertexID);
    vertexRadius = Radius;
    vertexSquirt = Squirt;
    vertexColor = Color;
}
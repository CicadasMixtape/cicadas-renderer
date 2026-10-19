#version 330

#moj_import <minecraft:projection.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:vertices.glsl>

in vec3 Position;
in vec2 Size;
in float Radius;
in vec4 Color;

out vec2 vertexSize;
out vec2 vertexCoord;
out float vertexRadius;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexSize = Size;
    vertexCoord = rvertexcoord(gl_VertexID);
    vertexRadius = Radius;
    vertexColor = Color;
}
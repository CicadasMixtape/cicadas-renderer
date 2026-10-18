#version 330

#moj_import <minecraft:projection.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

in vec3 Position;
in vec2 Size;
in vec2 Coord;
in float Radius;
in vec4 Color;

out vec2 vertexSize;
out vec2 vertexCoord;
out float vertexRadius;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexSize = Size;
    vertexCoord = Coord;
    vertexRadius = Radius;
    vertexColor = Color;
}
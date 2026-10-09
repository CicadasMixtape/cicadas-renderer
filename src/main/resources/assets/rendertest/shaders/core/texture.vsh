#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 Coord;
in vec2 Size;

out vec4 vertexColor;
out vec2 vertexCoord;
out vec2 vertexSize;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color;
    vertexCoord = Coord;
    vertexSize = Size;
}
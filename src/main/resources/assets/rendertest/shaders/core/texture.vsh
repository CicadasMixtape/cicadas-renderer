#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec2 Size;
in vec2 Coord;
in vec4 Color;

out vec2 vertexSize;
out vec2 vertexCoord;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexSize = Size;
    vertexCoord = Coord;
    vertexColor = Color;
}
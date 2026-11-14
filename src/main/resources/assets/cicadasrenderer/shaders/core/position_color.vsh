#version 330

#moj_import <cicadasrenderer:projection_uniform.glsl>

in vec3 Position;
in vec4 Color;

out vec4 vertexColor;

void main() {
    gl_Position = proj * modelView * vec4(Position, 1.0);
    vertexColor = Color;
}
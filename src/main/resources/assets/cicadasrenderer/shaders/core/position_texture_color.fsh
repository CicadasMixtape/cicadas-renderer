#version 330
#extension GL_ARB_separate_shader_objects : require

layout(location = 0) in vec2 vertexCoord;
layout(location = 1) in vec4 vertexColor;

uniform sampler2D Sampler0;

layout(location = 0) out vec4 fragColor;

void main() {
    fragColor = texture(Sampler0, vertexCoord) * vertexColor;
}
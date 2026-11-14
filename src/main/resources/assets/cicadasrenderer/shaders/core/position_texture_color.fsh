#version 330

in vec2 vertexCoord;
in vec4 vertexColor;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    fragColor = texture(Sampler0, vertexCoord) * vertexColor;
}
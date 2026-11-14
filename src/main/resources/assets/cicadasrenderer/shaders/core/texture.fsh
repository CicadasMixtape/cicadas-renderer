#version 330

#moj_import <cicadasrenderer:rect.glsl>

in vec2 vertexSize;
in vec2 vertexCoord;
in float vertexRadius;
in vec4 vertexColor;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, vertexCoord) * vertexColor;
    vec2 center = vertexSize / 2.0;
    float dist = rect(center - (vertexCoord * vertexSize), center - 1.0, vertexRadius);
    float alpha = 1.0 - smoothstep(-0.5, 0.5, dist);
    fragColor = vec4(color.rgb, color.a * alpha);
}
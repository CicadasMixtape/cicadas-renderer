#version 330

#moj_import <cicadasrenderer:squircle.glsl>

in vec2 vertexSize;
in vec2 vertexCoord;
in float vertexRadius;
in float vertexSquirt;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec2 center = vertexSize / 2.0;
    float dist = squircle(center - (vertexCoord * vertexSize), center - 1.0, vertexRadius, vertexSquirt);
    float alpha = 1.0 - smoothstep(-0.5, 0.5, dist);
    fragColor = vec4(vertexColor.rgb, vertexColor.a * alpha);
}
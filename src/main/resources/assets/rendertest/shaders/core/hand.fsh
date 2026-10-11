#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:screen_data.glsl>

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec2 vertexCoord;
in vec4 vertexColor;

uniform sampler2D sampler0;
uniform sampler2D sampler1;

layout(std140) uniform ColorData {
    vec4 tint;
};

out vec4 fragColor;

void main() {
    vec4 color = vec4(texture(sampler1, gl_FragCoord.xy / screenSize).rgb, texture(sampler0, vertexCoord).a);
    color *= tint;
    color *= vertexColor;
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
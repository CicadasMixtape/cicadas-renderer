#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:rect.glsl>
#moj_import <rendertest:screen_data.glsl>

in vec2 vertexSize;
in vec2 vertexCoord;
in float vertexRadius;
in vec4 vertexColor;

uniform sampler2D sampler0;

out vec4 fragColor;

void main() {
    vec4 color = texture(sampler0, gl_FragCoord.xy / screenSize) * vertexColor;
    vec2 center = vertexSize / 2.0;
    float distance = rect(center - (vertexCoord * vertexSize), center - 1.0, vertexRadius);
    float step = 1.0 - smoothstep(-0.5, 0.5, distance);
    fragColor = vec4(color.rgb, color.a * step) * ColorModulator;
}
#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:screen_data.glsl>

in vec2 vertexCoord;
in vec4 vertexColor;

uniform sampler2D sampler0;
uniform sampler2D sampler1;

layout(std140) uniform ColorData {
    vec4 tint;
};

out vec4 fragColor;

void main() {
    vec4 color = vec4(1.0);
    vec4 color0 = texture(sampler0, vertexCoord);
    vec4 color1 = texture(sampler1, gl_FragCoord.xy / screenSize);

    if (color0.a < 0.1) {
        discard;
    }

    color *= color1;
    color *= tint;
    color *= vertexColor;

    fragColor = color;
}
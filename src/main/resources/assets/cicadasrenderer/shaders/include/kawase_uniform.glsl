#ifndef CICADASRENDERER_KAWASE_UNIFORM_GLSL
#define CICADSRENDERER_KAWASE_UNIFORM_GLSL

layout(std140) uniform Kawase {
    float offset;
    vec2 halfPixel;
};

#endif
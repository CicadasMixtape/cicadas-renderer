#ifndef CICADASRENDERER_MSDF_UNIFORM_GLSL
#define CICADSRENDERER_MSDF_UNIFORM_GLSL

layout(std140) uniform Msdf {
    float range;
    float thickness;
};

#endif
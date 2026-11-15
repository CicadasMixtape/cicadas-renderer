#ifndef CICADASRENDERER_PROJECTION_UNIFORM_GLSL
#define CICADSRENDERER_PROJECTION_UNIFORM_GLSL

layout(std140) uniform Projection {
    mat4 proj;
    mat4 modelView;
};

#endif
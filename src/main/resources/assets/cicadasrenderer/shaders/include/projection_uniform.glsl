#version 330

layout(std140) uniform Projection {
    mat4 proj;
    mat4 modelView;
};
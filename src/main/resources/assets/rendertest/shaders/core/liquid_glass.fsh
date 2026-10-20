#version 330

#moj_import <rendertest:rect.glsl>
#moj_import <rendertest:liquid_uniform.glsl>
#moj_import <rendertest:screen_uniform.glsl>

in vec2 vertexSize;
in vec2 vertexCoord;
in float vertexRadius;
in vec4 vertexColor;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    vec2 center = vertexSize / 2.0;
    vec2 p = vertexCoord * vertexSize - center;

    float dist = rect(-p, center - 1.0, vertexRadius);
    float alpha = 1.0 - smoothstep(-0.5, 0.5, dist);

    vec2 uv = gl_FragCoord.xy / screenSize;
    vec2 d = vec2(dFdx(p.x), dFdy(p.y));
    d = sign(d) * max(abs(d), 1e-6);

    vec2 wc = (gl_FragCoord.xy - p / d) / screenSize;
    float bevel = min(center.x, center.y);
    float rb = pow(1.0 - clamp(-dist / bevel, 0.0, 1.0), power);
    vec2 lens = clamp(wc + (uv - wc) * (1.0 - rb * 0.5), 0.0, 1.0);
    vec4 color = vec4(texture(Sampler0, lens).rgb, 1.0) * vertexColor;

    fragColor = vec4(color.rgb, color.a * alpha);
}
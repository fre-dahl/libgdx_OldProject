#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

uniform sampler2D u_texture;
uniform vec4 u_tone;
uniform vec2 u_resolution;
uniform float u_waterPeriod;
uniform float u_time;

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

const float VIGNETTE_RADIUS_OUTER = 0.7;
const float VIGNETTE_RADIUS_INNER = 0.4;
const float COLOR_INTENSITY = 0.4;

#FUNCTION_INSERT

void main()
{
    // VIGNETTE
    vec2 relativePos = gl_FragCoord.xy / u_resolution - 0.5;
    float vignetteLen = length(relativePos);
    float vignette = smoothstep(VIGNETTE_RADIUS_OUTER, VIGNETTE_RADIUS_INNER, vignetteLen);

    // EDGE
    float edgeXlen = length(relativePos.x);
    float edgeX = smoothstep(0.6,0.35,edgeXlen);
    float edgeYlen = length(relativePos.y);
    float edgeY = smoothstep(0.6,0.45,edgeYlen);

    // NOISE
    //vec3 period = vec3(10000.0, 10000.0, u_waterPeriod);
    //float noiseX = pnoise(vec3(gl_FragCoord.xy * 0.125 * u_resolution.x / u_resolution.y , u_time), period);
    //float noiseY = pnoise(vec3(gl_FragCoord.xy, u_time), period);
    float noiseX = cnoice(vec2(gl_FragCoord.xy));
    float noiseY = cnoice(vec2(gl_FragCoord.xy));
    vec4 tex = texture2D(u_texture, v_texCoords + vec2(noiseX, noiseY) * 0.01);

    // MIXING
    tex.rgb = mix(tex.rgb, tex.rgb * vignette * edgeX * edgeY * u_tone.rgb, COLOR_INTENSITY);

    gl_FragColor = tex * v_color;
}
#version 150 core

in vec2 textureCoord;

uniform sampler2D textureImage;
uniform vec3 modelColor;
uniform mat3 texOffsetMatrix;

out vec4 fragColor;

void main(){
    // fragColor = vec4(1, 0 ,0, 1);
    // fragColor = vec4(textureCoord, 0, 1);
    vec4 textureColor = texture2D(textureImage, vec2(texOffsetMatrix*vec3(textureCoord,1)));
    // vec4 textureColor = texture2D(textureImage, textureCoord);
    if (textureColor.w < 0.001) discard;
    fragColor = textureColor*vec4(modelColor,1);
}
#version 150 core

in vec2 textureCoord;

uniform sampler2D textureImage;
uniform vec3 modelColor;
uniform mat3 texOffsetMatrix;

out vec4 fragColor;

void main(){
    // Multiplying textureCoord from vertex shader by texOffsetMatrix from Texture class
    vec2 transformedTexCoord = vec2(texOffsetMatrix*vec3(textureCoord,1));
    // Sampling texture
    vec4 textureColor = texture2D(textureImage, transformedTexCoord);
    // If textureColor.w < 0.001 discaring fragment
    if (textureColor.w < 0.001) discard;
    // Muliplying textureColor and modelColor from Object2D class
    fragColor = textureColor*vec4(modelColor,1);
}
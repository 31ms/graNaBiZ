package engine.graphics;

import math.*;

import static org.lwjgl.opengl.GL32.*;

public class Texture{
    public Vector2 offset = new Vector2(0,0);
    public Vector2 scale = new Vector2(1,1);

    public Image image;
    private int id;

    public Texture(){
        // Creating texture
        id = glGenTextures();
        Bind();
        // Setting wrapping and filtering
        setWrap(Texture.Enums.Wrap.CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        setFilter(Texture.Enums.Filter.NEAREST);
    }
    public Texture setWrap(int value){
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, value);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, value);
        return this;
    }
    public Texture setFilter(int value){
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, value);
        return this;
    }
    public Texture uploadImage(Image image){
        // Uploading buffer with pixels from image to texture
        glTexImage2D(GL_TEXTURE_2D,
            0, GL_RGBA8, image.width, image.height, 0, GL_RGBA,
           GL_UNSIGNED_BYTE, image.buffer);
        this.image = image;
        return this;
    }
    public Texture uploadPixels(int width, int height, byte[] p){
        // Creates image object from arguments and calls method
        return uploadImage(new Image(p, width, height));
    }
    public void Bind(){
        // Uploading transform matrix to ShaderProgram and binding texture
        var translationM = new Matrix3().setTranslation(this.offset);
        var scaleM = new Matrix3()._m00(this.scale.x)._m11(this.scale.y);
        ShaderProgram.Uniforms.setMatrix3(ShaderProgram.Uniforms.texOffsetMatrix,
            translationM.multiply(scaleM)
        );
        glBindTexture(GL_TEXTURE_2D, id);
    }
    public static Texture defaultTexture;
    public static void init(){
        // Creating default texture
        defaultTexture = new Texture().uploadImage(Image.defaultImage); 
    }
    public static class Enums {
        public static class Wrap {
            public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
            public static final int REPEAT = GL_REPEAT;
            public static final int MIRRORED_REPEAT = GL_MIRRORED_REPEAT;
            public static final int CLAMP_TO_BORDER = GL_CLAMP_TO_BORDER;
        }
        public static class Filter {
            public static final int LINEAR = GL_LINEAR;
            public static final int NEAREST = GL_NEAREST; 
        }
    }
}

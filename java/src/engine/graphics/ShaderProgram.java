package engine.graphics;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL32.*;

import engine.FileReader;
public class ShaderProgram {
    private static int id;
    static void init(){
        id = glCreateProgram();
        addShader("vertex.vert", GL_VERTEX_SHADER);
        addShader("fragment.frag", GL_FRAGMENT_SHADER);
        glLinkProgram(id);
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
        glValidateProgram(id);
        status = glGetProgrami(id, GL_VALIDATE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
        glUseProgram(id);

        Attributes.aPosition = getAttribLocation("aPosition");
    }
    private static void addShader(String filename, int target){
        int shader = glCreateShader(target);
        glShaderSource(shader, FileReader.readText("shaders/" + filename));
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }
        glAttachShader(id, shader);
    }
    private static int getAttribLocation(String name){
        return glGetAttribLocation(id, name);
    }
    public static void defineAttribs(){
        Attributes.defineAttrib(Attributes.aPosition, 2, 0);
            // this.defineAttrib("aVertexTexCoord", 2, 4, 2)
    }
    public static class Attributes {
        static int vertexSize = 4;
        static int aPosition;
        static int aTextureCoord;
        static void defineAttrib(int loc, int size, int vertexOffset){
            glEnableVertexAttribArray(loc);
            glVertexAttribPointer(loc, size, GL_FLOAT, false, vertexSize*4, vertexOffset*4);
        }
    }
}

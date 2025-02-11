package engine.graphics;
import math.*;
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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import engine.FileReader;
public class ShaderProgram {
    private static int id;
    static void init(){
        // Creating program, attaching shaders, linking, validating and using
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

        // Getting attribute and uniform locations and setting enums
        Attributes.aPosition = getAttribLocation("aPosition");
        Attributes.aTextureCoord = getAttribLocation("aTextureCoord");

        Uniforms.model = getUniformLocation("model");
        Uniforms.ortho = getUniformLocation("ortho");
        Uniforms.zindex = getUniformLocation("zindex");
        Uniforms.modelColor = getUniformLocation("modelColor");
        Uniforms.texOffsetMatrix = getUniformLocation("texOffsetMatrix");
    }
    private static void addShader(String filename, int target){
        // Creating shader and setting source
        int shader = glCreateShader(target);
        glShaderSource(shader, FileReader.readText("shaders/" + filename));
        // Compiling, validating and attaching shader to program
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }
        glAttachShader(id, shader);
    }
    private static int getAttribLocation(String name){
        // Getting attribute lcation
        return glGetAttribLocation(id, name);
    }
    private static int getUniformLocation(String name){
        // Getting uniform lcation
        return glGetUniformLocation(id, name);
    }
    public static void defineAttribs(){
        // Definig all attributes (Bind VertexArrayObject and VertexBufferObject first)
        Attributes.defineAttrib(Attributes.aPosition, 2, 0);
        Attributes.defineAttrib(Attributes.aTextureCoord, 2, 2);
    }
    public static class Attributes {
        public static final int vertexSize = 4;
        public static int aPosition;
        public static int aTextureCoord;
        public static void defineAttrib(int loc, int size, int vertexOffset){
            // Enabling attribute and setting pointer
            glEnableVertexAttribArray(loc);
            glVertexAttribPointer(loc, size, GL_FLOAT, false, vertexSize*4, vertexOffset*4);
        }
    }
    public static class Uniforms {
        public static int ortho;
        public static int model;
        public static int zindex;
        public static int modelColor;
        public static int texOffsetMatrix;
        // Setting uniforms value
        public static void setMatrix3(int loc, Matrix3 value){
            glUniformMatrix3fv(loc, false, value.toFloatBuffer());
        }
        public static void setVector3(int loc, Vector3 value){
            glUniform3fv(loc, value.toFloatBuffer());
        }
        public static void setFloat(int loc, float value){
            glUniform1f(loc, value);
        }
    }
}

package engine.graphics.buffers;

import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

import org.lwjgl.BufferUtils;

public class VertexBufferObject {
    public int id;
    public int target;
    public VertexBufferObject(int target){
        this.target = target;
        id = glGenBuffers();
    }
    public static VertexBufferObject vbo(){
        return new VertexBufferObject(GL_ARRAY_BUFFER);
    }
    public static VertexBufferObject ebo(){
        return new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    }
    public void Bind(){
        glBindBuffer(target,id);
    }
    public void uploadData(FloatBuffer data, int usage){
        glBufferData(target, data, usage);
    }
    public void uploadData(IntBuffer data, int usage){
        glBufferData(target, data, usage);
    }
    public void uploadData(float[] data, int usage){
        uploadData(BufferUtils.createFloatBuffer(data.length).put(data).flip(), usage);;
    }
    public void uploadData(int[] data, int usage){
        uploadData(BufferUtils.createIntBuffer(data.length).put(data).flip(), usage);
    }
    public void Destroy(){
        glDeleteBuffers(id);
    }
}

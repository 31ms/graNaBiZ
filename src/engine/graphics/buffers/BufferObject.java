package engine.graphics.buffers;

import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

import org.lwjgl.BufferUtils;

public class BufferObject {
    private int id;
    private int target;
    public BufferObject(int target){
        // Creating BufferObject and setting target
        this.target = target;
        id = glGenBuffers();
    }
    // Calling constructor wit ARRAY_BUFFER/ELEMENT_ARRAY_BUFFER target
    public static BufferObject VertexBufferObject(){
        return new BufferObject(GL_ARRAY_BUFFER);
    }
    public static BufferObject ElementBufferObject(){
        return new BufferObject(GL_ELEMENT_ARRAY_BUFFER);
    }
    // Binding Buffer to the object's target
    public void Bind(){
        glBindBuffer(target,id);
    }
    // Uploading Float/Int Buffer to BufferObject
    public void uploadData(FloatBuffer data, int usage){
        glBufferData(target, data, usage);
    }
    public void uploadData(IntBuffer data, int usage){
        glBufferData(target, data, usage);
    }
    // Creating Float/Int Buffer from array and calls method
    public void uploadData(float[] data, int usage){
        uploadData(BufferUtils.createFloatBuffer(data.length).put(data).flip(), usage);;
    }
    public void uploadData(int[] data, int usage){
        uploadData(BufferUtils.createIntBuffer(data.length).put(data).flip(), usage);
    }
    // Destroying BufferObject
    public void Destroy(){
        glDeleteBuffers(id);
    }
}

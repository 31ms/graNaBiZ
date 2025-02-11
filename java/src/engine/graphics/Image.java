package engine.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Image {
    public ByteBuffer buffer;
    public int width, height;
    public Image(ByteBuffer buffer, int width, int height){
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }
    public Image(byte[] buffer, int width, int height){
        this(BufferUtils.createByteBuffer(buffer.length).put(buffer).flip(), width, height);
    }
    public static final Image defaultImage = new Image(new byte[]{0, 0, 0, -1,  -1, 0, -1, -1,
        -1, 0, -1, -1,  0, 0, 0, -1}, 2, 2);
}

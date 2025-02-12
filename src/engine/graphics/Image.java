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
        // Creating buffer from array and calling other constructor
        this(BufferUtils.createByteBuffer(buffer.length).put(buffer).flip(), width, height);
    }
    // Setting default image
    public static final Image defaultImage = new Image(new byte[]{0, 0, 0, -1,  -1, 0, -1, -1,
        -1, 0, -1, -1,  0, 0, 0, -1}, 2, 2);
    public static final Image whiteImage = new Image(new byte[]{-1, -1, -1, -1}, 1, 1);
}

package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import engine.graphics.Image;

import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class FileReader {
    private static ClassLoader loader = FileReader.class.getClassLoader();
    public static String readText(String path){
        InputStream is = loader.getResourceAsStream("resources/" + path);
        if (is == null){
            System.out.println("File Reader: No text resource found on path: " + path);
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }  
    public static Image readImage(String path){
        InputStream is = loader.getResourceAsStream("resources/" + path);
        if (is == null) {
            System.out.println("File Reader: No image resource found on path: " + path);
            return null;
        }
        byte[] bytes = {};
        ByteBuffer Bbuffer;
        try {
            bytes = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Bbuffer = ByteBuffer.allocateDirect(bytes.length);
        Bbuffer.put(bytes);
        Bbuffer.flip();
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);
        
        ByteBuffer buffer = stbi_load_from_memory(Bbuffer, w, h, c, 4);
        return new Image(buffer, w.get(), h.get());
    }
}

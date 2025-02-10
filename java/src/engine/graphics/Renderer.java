package engine.graphics;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;

import engine.Window;

public class Renderer {
    private static Window window; 
    public static void init(Window window){
        Renderer.window = window;
        window.Bind();
        createCapabilities();
        ShaderProgram.init();
        Geometry.init();
        glClearColor(0, .2f, .1f, 1);
    }
    public static void clear(){
        glClear(GL_COLOR_BUFFER_BIT);
    }
    public static void render(){
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);}
    public static Window getWindow(){
        return window;
    }
}

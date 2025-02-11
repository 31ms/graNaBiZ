package engine;

// import org.lwjgl.system.*;
// import org.lwjgl.opengl.*;
// import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.graphics.Renderer;
import math.Vector2;

public class Window {
    
    private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private static GLFWWindowSizeCallback sizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int width, int height){
            mainWindow.width = width;
            mainWindow.height = height;
            Renderer.onWindowSizeChange(width, height);
        } 
    };
    public static void initGLFW(){
        glfwSetErrorCallback(errorCallback);
        if (!glfwInit()) {
            throw new IllegalStateException("unable to initialize");
        }
        primaryMonitor = glfwGetPrimaryMonitor();
        GLFWVidMode vm = glfwGetVideoMode(primaryMonitor);
        monitorWidth = vm.width();
        monitorHeight = vm.height();
        glfwDefaultWindowHints();
    }
    public static long primaryMonitor;
    public static int[] getMonitorSize(){ 
        return new int[]{monitorWidth, monitorHeight};
    }
    public static int monitorWidth, monitorHeight;
    public static Window mainWindow;
    public int width;
    public int height;
    public Vector2 getSize(){
        return new Vector2(width, height);
    }
    public long monitor;
    public long id;
    public void Hints(){
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    }
    public Window(String name){
        initGLFW();
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        create(monitorWidth, monitorHeight, name);;
    }
    public Window(int w, int h, String name){
        initGLFW();
        create(w, h, name);
    }
    private void create(int w, int h, String name){
        width = w;
        height = h;
        Hints();
        id = glfwCreateWindow(w, h, name, NULL, NULL);
        mainWindow = this;
    }
    public void Error(){
        if (id == NULL){
            glfwTerminate();
            throw new IllegalStateException("window sie nie zrobilo");
        }
    }
    public void Bind(){
        glfwMakeContextCurrent(id);
    }
    public void closeWindow(){
        glfwSetWindowShouldClose(id, true);
    }
    public boolean shouldClose(){
        return glfwWindowShouldClose(id);
    }
    public void swap(){
        glfwSwapBuffers(id);
    }
    public void Destroy(){
        freeCallbacks();
        glfwDestroyWindow(id);
    }
    public void setCallbacks(){
        Input.setInputCallbacks(id);
        glfwSetWindowSizeCallback(id, sizeCallback);
    }
    public void freeCallbacks(){
        Input.freeCallbacks();
        errorCallback.free();
        sizeCallback.free();
    }
}

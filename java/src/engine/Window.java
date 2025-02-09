package engine;

// import org.lwjgl.system.*;
// import org.lwjgl.opengl.*;
// import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.math.Vector2i;

// import static main.Main.main;
// import main.*;

public class Window {
    
    private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private static GLFWWindowSizeCallback sizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int width, int height){
            mainWindow.width = width;
            mainWindow.height = height;
            Rendering.mainRender.onWindowSizeChange(width, height);
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
    }
    public static long primaryMonitor;
    public static int[] getMonitorSize(){ 
        return new int[]{monitorWidth, monitorHeight};
    }
    public static int monitorWidth, monitorHeight;
    public static Window mainWindow;
    public int width;
    public int height;
    public long monitor;
    public long id;
    public void Hints(){
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    }
    public Window(int w, int h, boolean fullscr){
        if (fullscr){
            Vector2i size = getMonitorSize();
            width = size.x;
            height = size.y;
            monitor = primaryMonitor;
        } else {
            width = w;
            height = h;
            monitor = 0;
        }
        id = glfwCreateWindow(width, height, "dupa", monitor, NULL);
        mainWindow = this;
    }
    public Window(boolean fullscr){
        this(0, 0, true);
    }
    public Window(int w, int h){
        this(w, h, false);
    }
    // public Window(int w, int h, long mon)
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
    public void Destroy(){
        freeCallbacks();
        errorCallback.free();
        sizeCallback.free();
        glfwDestroyWindow(id);
    }
    public static void setCallbacks(){
        Input.setInputCallbacks(mainWindow);
        glfwSetWindowSizeCallback(mainWindow.id, sizeCallback);
    }
    public static void freeCallbacks(){
        Input.freeCallbacks();
    }
}

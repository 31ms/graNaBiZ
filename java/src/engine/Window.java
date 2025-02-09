package engine;

// import org.lwjgl.system.*;
// import org.lwjgl.opengl.*;
// import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

// import static main.Main.main;
// import main.*;

public class Window {
    
    private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private static GLFWWindowSizeCallback sizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int width, int height){
            mainWindow.width = width;
            mainWindow.height = height;
            // TU DAJ FUNKCJE CO MA SIE WYWOLAC PODCZAS ZMIANY OKNA
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
    // public Window(int w, int h, boolean fullscr, String name){
    //     if (fullscr){
    //         int[] size = getMonitorSize();
    //         width = size[0];
    //         height = size[1];
    //         monitor = primaryMonitor;
    //     } else {
    //         width = w;
    //         height = h;
    //         monitor = 0;
    //     }
    //     id = glfwCreateWindow(width, height, name, monitor, NULL);
    //     mainWindow = this;
    // }
    public Window(String name){
        initGLFW();
        int[] size = getMonitorSize();
        width = size[0];
        height = size[1];
        Hints();
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        id = glfwCreateWindow(width, height, name, NULL, NULL);
        
        // glfwSetWindowPos(id, 0, 0);
        mainWindow = this;
    }
    public Window(int w, int h, String name){
        initGLFW();
        width = w;
        height = h;
        monitor = 0;
        Hints();
        id = glfwCreateWindow(width, height, name, monitor, NULL);
        mainWindow = this;
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

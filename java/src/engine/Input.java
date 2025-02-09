package engine;

import org.lwjgl.glfw.*;

import math.*;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

public class Input {
    public static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window,int key, int scancode, int action, int mods){
            // System.out.println(key + "  " + action);
            if (action == GLFW_PRESS){
                Keyboard.hold[key] = true;
                Keyboard.down[key] = true;
                return;
            }
            if (action == GLFW_RELEASE){
                Keyboard.hold[key] = false;
                Keyboard.up[key] = true;
                return;
            }
        }
    };
    public static GLFWMouseButtonCallback mouseCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            // GLFW_MOUSE_BUTTON_1
            if (action == GLFW_PRESS){
                Mouse.hold[button] = true;
                Mouse.down[button] = true;
                return;
            } if (action == GLFW_RELEASE){
                Mouse.hold[button] = false;
                Mouse.up[button] = true;
                return;
            }
        }
    };
    // public static Vector2f Mouse = new Vector2f();
    // public static Vector2f mouseLast = new Vector2f();
    public static GLFWCursorPosCallback cursorCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos){
            // Mouse.lastPosition = Mouse.position.copy();  // ODKOMENTUJ JAK BEDZIE MATH
            // Mouse.position.set((float)xpos, (float)ypos);
            // Mouse.movement = Mouse.position.subtract(Mouse.lastPosition);
            // if (Mouse.justEntered){
            //     Mouse.movement.set(0, 0);
            //     Mouse.justEntered = false;
            // }
        }
    };
    
    public static GLFWCursorEnterCallback enterCallback = new GLFWCursorEnterCallback() {
        @Override
        public void invoke(long window, boolean enter){
            // Mouse.justEntered = true; // ODKOMENTUJ JAK BEDZIE MATH
        }
    };
    public static void setInputCallbacks(Window window){
        glfwSetKeyCallback(window.id, keyCallback);
        glfwSetMouseButtonCallback(window.id, mouseCallback);
        glfwSetCursorPosCallback(window.id, cursorCallback);
        glfwSetCursorEnterCallback(window.id, enterCallback);
    }
    public static void updateInput(){
        Arrays.fill(Keyboard.down, false);
        Arrays.fill(Keyboard.up, false);
        Arrays.fill(Mouse.down, false);
        Arrays.fill(Mouse.up, false);
        // Mouse.movement.set(0, 0); // ODKOMENTUJ JAK BEDZIE MATH
        glfwPollEvents();
    }
    public static void freeCallbacks(){
        keyCallback.free();
        mouseCallback.free();
        cursorCallback.free();
        enterCallback.free();
    }
    
    // public static boolean getMouseDown(int key){
    //     return mouseDown[key];
    // }
    // public static boolean getMouseUp(int key){
    //     return Keyboard.up[key];
    // }
    // public static boolean getMouse(int key){
    //     return Keyboard.hold[key];
    // }
    public static class Mouse {
        // private static Vector2f position = new Vector2f();
        // private static Vector2f lastPosition = new Vector2f(); // ODKOMENTUJ JAK BEDZIE MATH
        // private static Vector2f movement = new Vector2f();
        // private static boolean justEntered = false;
        // public static Vector2f getPosition(){
        //     return position.copy();
        // }
        // public static Vector2f getMovement(){
        //     return movement.copy();
        // }
        private static boolean[] hold = new boolean[8];
        private static boolean[] down = new boolean[8];
        private static boolean[] up = new boolean[8];
        public static boolean getDown(int button){
            return down[button];
        }
        public static boolean getUp(int button){
            return up[button];
        }
        public static boolean getHold(int button){
            return hold[button];
        }
        public static final int LEFT = GLFW_MOUSE_BUTTON_1;
        public static final int RIGHT = GLFW_MOUSE_BUTTON_2;
    } 
    public static class Keyboard {
        private static boolean[] hold = new boolean[348];
        private static boolean[] down = new boolean[348];
        private static boolean[] up = new boolean[348];
        public static boolean getDown(int key){
            return down[key];
        }
        public static boolean getUp(int key){
            return up[key];
        }
        public static boolean getHold(int key){
            return hold[key];
        }
        public static final int ESC = GLFW_KEY_ESCAPE;
        public static final int SPACE = GLFW_KEY_SPACE;
        public static final int Q = GLFW_KEY_Q;
        public static final int W = GLFW_KEY_W;
        public static final int E = GLFW_KEY_E;
        public static final int R = GLFW_KEY_R;
        public static final int T = GLFW_KEY_T;
        public static final int Y = GLFW_KEY_Y;
        public static final int U = GLFW_KEY_U;
        public static final int I = GLFW_KEY_I;
        public static final int O = GLFW_KEY_O;
        public static final int P = GLFW_KEY_P;
        public static final int A = GLFW_KEY_A;
        public static final int S = GLFW_KEY_S;
        public static final int D = GLFW_KEY_D;
        public static final int F = GLFW_KEY_F;
        public static final int G = GLFW_KEY_G;
        public static final int H = GLFW_KEY_H;
        public static final int J = GLFW_KEY_J;
        public static final int K = GLFW_KEY_K;
        public static final int L = GLFW_KEY_L;
        public static final int Z = GLFW_KEY_Z;
        public static final int X = GLFW_KEY_X;
        public static final int C = GLFW_KEY_C;
        public static final int V = GLFW_KEY_V;
        public static final int B = GLFW_KEY_B;
        public static final int N = GLFW_KEY_N;
        public static final int M = GLFW_KEY_M;
        public static final int RIGHT = GLFW_KEY_RIGHT;
        public static final int LEFT = GLFW_KEY_LEFT;
        public static final int UP = GLFW_KEY_UP;
        public static final int DOWN = GLFW_KEY_DOWN;
    }
}

package engine;
import static org.lwjgl.glfw.GLFW.*;
public class Timer {
    public static double timeCount;
    private static int fpsCount;
    public static int fps;
    private static int upsCount;
    public static int ups;
    private static double lastLoopTime;
    public static double delta;
    public static double time;
    public static void Update(){
        time = glfwGetTime();
        delta = time - lastLoopTime;
        lastLoopTime = time;
        timeCount += delta;
        upsCount++;
        fpsCount++;
        if (timeCount > 1){
            fps = fpsCount;
            fpsCount = 0;
            ups = upsCount;
            upsCount = 0;
            timeCount -= 1;
            // System.out.println(fps);
            // System.out.println(ups);
        }
    }
    public static void sleep(int time){
        try{
            Thread.sleep(time);
        } catch(InterruptedException e){
            System.out.println("got interrupted!");
        }
    }
    public static double getTime(){
        return glfwGetTime();
    }
}

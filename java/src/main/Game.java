package main;

import engine.*;
import math.*;
import engine.physics.*;
import engine.graphics.*;
public class Game {
    static Game game;
    Window window;
    public Game(){
        window = new Window(1000, 1000, "okno");
        Renderer.init(window);
        window.setCallbacks();
        Geometry.rect.Bind();
        PhysicsObject2D a = new PhysicsObject2D(Geometry.rect, 0, false);
        PhysicsObject2D b = new PhysicsObject2D(Geometry.rect, 0, false);
        a.size = new Vector2(100, 200);
        b.texture = new Texture()
        .uploadImage(FileReader.readImage("testbackground.png")).setWrap(Texture.Enums.Wrap.REPEAT);
        a.color = new Vector3(1, 1, 0);
        b.size = new Vector2(150, 150);
        b.zindex = 1;
        double c = 0;
        // System.out.println(Object2D.scene);
        while (!window.shouldClose()){
            Input.updateInput();
            b.texture.offset.x += .01;
            a.position = new Vector2(0, 300*Math.sin(c/2));
            System.out.println(CollisionChecker.checkCollisions(0, a).size());
            c+=0.4;
            Renderer.clear();
            Renderer.render(Object2D.scene);
            window.swap();
            Timer.sleep(16);
        };
    }
    public static void main(String[] args){
       game = new Game();
    }
}

package main;

import engine.*;
import math.*;
import engine.physics.*;
import engine.graphics.*;
public class Game {
    static Game game;
    Window window;
    public Game(){
        Scene scene = new Scene();
        Object2D.setScene(scene);
        window = new Window(1000, 800, "okno");
        Renderer.init(window);
        PhysicsObject2D a = new PhysicsObject2D(Geometry.rect, 0, false);
        PhysicsObject2D b = new PhysicsObject2D(Geometry.rect, 0, false);
        a.size = new Vector2(100, 200);
        Image testImage = FileReader.readImage("testbackground.png");
        a.color = new Vector4(1, 1, 0, 1);
        b.size = new Vector2(150, 150);
        b.zindex = .9f;
        double c = 0;
        Background background = new Background();
        background.texture.uploadImage(testImage);
        Object2D transparent = new Object2D(Geometry.rect, true);
        transparent.texture = new Texture().uploadPixels(1, 1, new byte[]{-1, -1, -1, -1});
        transparent.color = new Vector4(1, 0, 0, .5);
        while (!window.shouldClose()){
            Input.updateInput();
            background.align();
            background.texture.offset.x += .001;
            a.position = new Vector2(0, 300*Math.sin(c/2));
            // System.out.println(CollisionChecker.checkCollisions(0, a).size());
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

package math;

public class Line2 extends Vector2{
    public Vector2 origin = new Vector2(0,0);
    public Line2(Vector2 origin, Vector2 direction){
        super(direction.x, direction.y);
        this.origin = origin;
    }
}
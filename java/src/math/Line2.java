package math;

class Line2 extends Vector2{
    Vector2 origin = new Vector2(0,0);
    Line2(Vector2 origin, Vector2 direction){
        super(direction.x, direction.y);
        this.origin = origin;
    }
}
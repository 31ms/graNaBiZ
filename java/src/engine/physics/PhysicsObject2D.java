package engine.physics;
import engine.*;
import engine.graphics.*;
import math.*;
public class PhysicsObject2D extends Object2D{
    PhysicsObject2D(Geometry geometry, int group, boolean isIrreversible){
        super(geometry);
        //CollisionChecker.collisionGroups[group].push(this);
        this.isIrreversible = isIrreversible;
      }
    boolean isIrreversible = false;
    public Line2[] getAxis(){
        Vector2 RX = Matrix2.rotation(this.rotation).transformVector(new Vector2(1,0));
        Vector2 RY = Matrix2.rotation(this.rotation).transformVector(new Vector2(0,1));
        return new Line2[]{
            new Line2(this.position, RX),
            new Line2(this.position, RY)};
    }
    public Vector2[] getCorners(){
        Line2[] axis = this.getAxis();
        Vector2 RX = axis[0].scale(this.size.x/2);
        Vector2 RY = axis[1].scale(this.size.y/2);
        return new Vector2[]{
            this.position.add(RX).add(RY),
            this.position.add(RX.scale(-1)).add(RY),
            this.position.add(RX).add(RY.scale(-1)),
            this.position.add(RX.scale(-1)).add(RY.scale(-1))
        };
    }
    double getSignedDistance(Line2 line, Vector2 corner){
    Vector2 projected = corner.project(line);
      Vector2 CP = projected.sub(this.position);
      boolean sign = (CP.x * line.x) + (CP.y * line.y) > 0;
      return CP.length() * (sign ? 1 : -1);
    }
  }

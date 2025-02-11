package engine.physics;

import math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CollisionChecker{
    public static ArrayList<ArrayList<PhysicsObject2D>> collisionGroups = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    // Checking collisions between two specific instances of PhysicsObject2D
    public static boolean isColliding(PhysicsObject2D rect, PhysicsObject2D onRect){
        Vector2[] onRectCorners = onRect.getCorners();
        Vector2[] corners = rect.getCorners();
        ArrayList<Double> onThisAxis1 = new ArrayList<>();
        ArrayList<Double> onThisAxis2 = new ArrayList<>();
        ArrayList<Double> onOtherAxis1 = new ArrayList<>();
        ArrayList<Double> onOtherAxis2 = new ArrayList<>();
        // Getting the signed distance of each corner projected on the two rectangles axis
        for (int i = 0; i<onRectCorners.length; i++){
            onThisAxis1.add(rect.getSignedDistance(rect.getAxis()[0], onRectCorners[i]));
            onThisAxis2.add(rect.getSignedDistance(rect.getAxis()[1], onRectCorners[i]));
            onOtherAxis1.add(rect.getSignedDistance(onRect.getAxis()[0], corners[i]));
            onOtherAxis2.add(rect.getSignedDistance(onRect.getAxis()[1], corners[i]));
        }
        // Checking if the projected images of the rectangles are intersecting with them
        boolean isAxis1Colliding = ((Collections.min(onThisAxis1) < 0 && Collections.max(onThisAxis1) > 0) || Math.abs(Collections.min(onThisAxis1)) < rect.size.x/2 || Math.abs(Collections.max(onThisAxis1)) < rect.size.x/2);
        boolean isAxis2Colliding = ((Collections.min(onThisAxis2) < 0 && Collections.max(onThisAxis2) > 0) || Math.abs(Collections.min(onThisAxis2)) < rect.size.y/2 || Math.abs(Collections.max(onThisAxis2)) < rect.size.y/2);
        boolean isAxis3Colliding = ((Collections.min(onOtherAxis1) < 0 && Collections.max(onOtherAxis1) > 0) || Math.abs(Collections.min(onOtherAxis1)) < onRect.size.x/2 || Math.abs(Collections.max(onOtherAxis1)) < onRect.size.x/2);
        boolean isAxis4Colliding = ((Collections.min(onOtherAxis2) < 0 && Collections.max(onOtherAxis2) > 0) || Math.abs(Collections.min(onOtherAxis2)) < onRect.size.y/2 || Math.abs(Collections.max(onOtherAxis2)) < onRect.size.y/2);
        // true is only returned if projections on all axis are intersecting
        return (isAxis1Colliding && isAxis2Colliding && isAxis3Colliding && isAxis4Colliding);
    }
    // Checks broad collisions between two objects
    public static boolean isCollidingBroad(PhysicsObject2D rect, PhysicsObject2D onRect){
        boolean isColliding = true;
        if(rect.isIrreversible && onRect.isIrreversible){
            isColliding = (
                rect.position.x-rect.size.x/2 < onRect.position.x+onRect.size.x/2 &&
                onRect.position.x-onRect.size.x/2 < rect.position.x+rect.size.x/2 &&
                rect.position.y+rect.size.y/2 > onRect.position.y-onRect.size.y/2 &&
                onRect.position.y+onRect.size.y/2 > rect.position.y-rect.size.y/2
            );
        }
        else{
            double rectDiagonal = Math.hypot(rect.size.x, rect.size.y);
            double onRectDiagonal = Math.hypot(onRect.size.x, onRect.size.y);
            isColliding = (
                rect.position.x-rectDiagonal/2 < onRect.position.x+onRectDiagonal/2 &&
                onRect.position.x-onRectDiagonal/2 < rect.position.x+rectDiagonal/2 &&
                rect.position.y+rectDiagonal/2 > onRect.position.y-onRectDiagonal/2 &&
                onRect.position.y+onRectDiagonal/2 > rect.position.y-rectDiagonal/2
            );
        }
        return isColliding;
    }
    // Checking collisions between a specified object and all other objects in a specified collision group
    public static ArrayList<PhysicsObject2D> checkCollisions(int group, PhysicsObject2D onRect){
        ArrayList<PhysicsObject2D> collidesWith = checkCollisionsBroad(group, onRect);
        // The narrow phase of checking collisions
        for(int i = 0; i<CollisionChecker.collisionGroups.get(group).size(); i++){
            if(CollisionChecker.isColliding(onRect, CollisionChecker.collisionGroups.get(group).get(i))){
                if(onRect !=CollisionChecker.collisionGroups.get(group).get(i)){
                    collidesWith.add(CollisionChecker.collisionGroups.get(group).get(i));
                }
            }
        }
        // Method returns an array with all colliding objects or an empty one if there are no collisions detected
        return collidesWith;
    }
    // The broad phase of checking collisions, checks collisions between object's AABBs
    // For irreversible objects this is a full collision test
    public static ArrayList<PhysicsObject2D> checkCollisionsBroad(int group, PhysicsObject2D onRect){
        ArrayList<PhysicsObject2D> collidesWith = new ArrayList<>();
        for(int i = 0; i<CollisionChecker.collisionGroups.get(group).size(); i++){
            if(CollisionChecker.isCollidingBroad(onRect, CollisionChecker.collisionGroups.get(group).get(i))){
                if(onRect !=CollisionChecker.collisionGroups.get(group).get(i)){
                    collidesWith.add(CollisionChecker.collisionGroups.get(group).get(i));
                }
            }
        }
        // Method returns an array with all colliding objects or an empty one if there are no collisions detected
        return collidesWith;
    }
}
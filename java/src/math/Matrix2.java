package math;

import static org.lwjgl.opengl.GL20.glGetUniformfv;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix2{
    double m00 = 1; double m01 = 0;
    double m10 = 0; double m11 = 1;
    Matrix2 _m00(double m00){this.m00 = m00;return this;}
    Matrix2 _m10(double m10){this.m10 = m10;return this;}
    Matrix2 _m01(double m01){this.m01 = m01;return this;}
    Matrix2 _m11(double m11){this.m11 = m11;return this;}
    Vector2[] baseVectors(){
        return new Vector2[]{
            new Vector2(this.m00, this.m10),
            new Vector2(this.m01, this.m11)
        };
    }
    Vector2 transformVector(Vector2 vector) {
        double x = this.m00 * vector.x + this.m01 * vector.y;
        double y = this.m10 * vector.x + this.m11 * vector.y;
        return new Vector2(x, y);
    }
    Matrix2 multiply(Matrix2 matrix){
        Matrix2 m = new Matrix2();
        m.m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10;
        m.m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10;

        m.m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11;
        m.m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11;
        return m;
    }
    static Matrix2 rotation(double angle){
        Matrix2 m = new Matrix2();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        m.m00 = c;
        m.m11 = c;
        m.m01 = -s;
        m.m10 = s;
        return m;
    }
    
    @Override
    public String toString() {
        return 
        this.m00 + this.m01 + "\n" +
        this.m10 + this.m11;
    }
    float[] toArray(){
        return new float[]{(float)this.m00, (float)this.m10, (float)this.m01, (float)this.m11};
    }
    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(4).put(toArray()).flip();
    }
}

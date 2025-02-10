package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix3{
    public double m00 = 1; double m01 = 0; double m02 = 0;
    public double m10 = 0; double m11 = 1; double m12 = 0;
    public double m20 = 0; double m21 = 0; double m22 = 1;
    public Matrix3 _m00(double m00){this.m00 = m00;return this;}
    public Matrix3 _m10(double m10){this.m10 = m10;return this;}
    public Matrix3 _m20(double m20){this.m20 = m20;return this;}
    public Matrix3 _m01(double m01){this.m01 = m01;return this;}
    public Matrix3 _m11(double m11){this.m11 = m11;return this;}
    public Matrix3 _m21(double m21){this.m21 = m21;return this;}
    public Matrix3 _m02(double m02){this.m02 = m02;return this;}
    public Matrix3 _m12(double m12){this.m12 = m12;return this;}
    public Matrix3 _m22(double m22){this.m22 = m22;return this;}
    public Vector3[] baseVectors(){
        return new Vector3[]{
            new Vector3(this.m00, this.m10, this.m20),
            new Vector3(this.m01, this.m11, this.m21),
            new Vector3(this.m02, this.m12, this.m22)
        };
    }
    public Vector3 transformVector(Vector3 vector) {
        var x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z;
        var y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z;
        var z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z;
        return new Vector3(x, y, z);
    }
    public Vector2 transformVector2(Vector2 vector, double z){
        Vector3 vec3 = this.transformVector(new Vector3(vector.x, vector.y, z));
        return new Vector2(vec3.x, vec3.y);
    }
    public Matrix3 multiply(Matrix3 matrix){
        var m = new Matrix3();
        m.m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20;
        m.m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20;
        m.m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20;

        m.m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21;
        m.m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21;
        m.m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21;

        m.m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22;
        m.m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22;
        m.m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22;
        return m;
    }
    public Matrix3 setTranslation(Vector2 vec2){
        this.m02 = vec2.x;
        this.m12 = vec2.y;
        return this;
    }
    public static Matrix3 rotation(double angle){
        var m = new Matrix3();
        var c = Math.cos(angle);
        var s = Math.sin(angle);
        m.m00 = c;
        m.m11 = c;
        m.m01 = -s;
        m.m10 = s;
        return m;
    }
    public static Matrix3 ortho(double left, double right, double bottom, double top){
        double xt = (left+right)/(left - right);
        double xs = 2/(right - left);

        double yt = (bottom+top)/(bottom - top);
        double ys = 2/(top - bottom);
        Matrix3 m = new Matrix3();
        return m._m00(xs)._m02(xt)._m11(ys)._m12(yt);
    }
    @Override
    public String toString() {
        return 
        this.m00 + this.m01 + this.m02 + "\n" + 
        this.m10 + this.m11 + this.m12 + "\n" + 
        this.m20 + this.m21 + this.m22;
    }
    public float[] toArray(){
        return new float[]{
            (float)this.m00, (float)this.m10, (float)this.m20,
            (float)this.m01, (float)this.m11, (float)this.m21,
            (float)this.m02, (float)this.m12, (float)this.m22};
    }
    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(9).put(toArray()).flip();
    }
}
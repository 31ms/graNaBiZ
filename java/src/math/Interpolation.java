package math;

public class Interpolation {
    public static double fade(double t){
        return ((6*t - 15)*t + 10)*t*t*t;
    }
    public static double lerp(double t,  double a, double b){
        return a + t*(b-a);
    }
    public static double inverseLerp(double a, double b, double o)
    {
        return (o - a)/(b - a);
    }
    public static double smooth(double t, double a,  double b){
        return Interpolation.lerp(Interpolation.fade(t), a, b);
    }
    public static double square (double x, double y, double leftBottom, double leftTop, double rightBottom, double rightTop){
        return Interpolation.smooth(y, Interpolation.smooth(x, leftBottom, rightBottom), Interpolation.smooth(x, leftTop, rightTop));
    }
}

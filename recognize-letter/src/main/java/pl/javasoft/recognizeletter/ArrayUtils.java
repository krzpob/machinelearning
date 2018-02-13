package pl.javasoft.recognizeletter;

public class ArrayUtils {

    public static double[] diff(double a[], double b[]){
        double[] result = new double[a.length];
        for (int i=0;i<a.length;i++){
            result[i]=a[i]-b[i];
        }
        return result;
    }

    static double product(double[] a, double[] b){
        double result=0;
        for(int i=0;i<a.length; i++){
            result+=a[i]*b[i];
        }
        return result;
    }
}

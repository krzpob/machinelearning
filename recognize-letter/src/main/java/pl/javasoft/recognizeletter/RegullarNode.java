package pl.javasoft.recognizeletter;

import java.util.stream.DoubleStream;

public abstract class RegullarNode implements Node {

    protected double result;

    protected double[] w;

    protected double[] a;

    RegullarNode(int size) {
        a = new double[size];
        w = DoubleStream.generate(Math::random).limit(a.length).toArray();
    }

    double product(){
       return ArrayUtils.product(a,w);
    }

    public abstract void teach(double[] delta);

}

package pl.javasoft.recognizeletter;

import com.codepoetics.protonpack.StreamUtils;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class HiddenNode implements Node{

    private double result=0;

    private double[] weights;

    public HiddenNode(int sizeOfInput) {
        weights = DoubleStream.generate(Math::random).limit(sizeOfInput).toArray();
    }

    @Override
    public void calc(Node a[]) {
        int index;
        StreamUtils.zipWithIndex(Stream.of(a)).forEach(
                nodeIndexed -> {
                    result+=Math.abs(weights[((int) nodeIndexed.getIndex())]-nodeIndexed.getValue().get())/40.0;
                }
        );

    }

    @Override
    public double get() {
        return result;
    }
}

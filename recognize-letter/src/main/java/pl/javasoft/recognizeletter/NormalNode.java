package pl.javasoft.recognizeletter;

import com.codepoetics.protonpack.StreamUtils;
import lombok.Getter;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class NormalNode implements Node {

    private double[] weights;

    private double result=0;

    public NormalNode(int sizeOfInput) {
        weights = DoubleStream.generate(Math::random).limit(sizeOfInput).toArray();
    }

    @Override
    public void calc(Node[] a) {
        StreamUtils.zipWithIndex(Stream.of(a)).forEach(
                nodeIndexed -> {
                    result+=Math.abs(weights[(int)nodeIndexed.getIndex()]-nodeIndexed.getValue().get())/26;
                }
        );

    }

    @Override
    public double get() {
        return result;
    }
}

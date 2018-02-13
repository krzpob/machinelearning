package pl.javasoft.recognizeletter;

import java.util.Arrays;
import java.util.stream.Stream;

public class NormalNode extends RegullarNode implements Node{
    private double corection=0.3;

    public NormalNode(int size, HiddenNode[] input) {
        super(size);
        Arrays.fill(w,1);
        a = Stream.of(input).mapToDouble(Node::get).toArray();
    }

    @Override
    public void calc(ActivateFunction activateFunction) {
        result = product()/40;
    }

    @Override
    public double get() {
        return result;
    }

    @Override
    public void teach(double[] delta) {

    }

    public void setA(Node[] nodes) {
        a=Stream.of(nodes).mapToDouble(Node::get).toArray();
    }
}

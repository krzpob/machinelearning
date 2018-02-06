package pl.javasoft.recognizeletter;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class InputNode implements Node {
    static private int SIZE=96;
    private long x;
    private long y;
    private int value;



    @Override
    public void calc(Node[] a) {

    }

    @Override
    public double get() {
        return value;
    }
}

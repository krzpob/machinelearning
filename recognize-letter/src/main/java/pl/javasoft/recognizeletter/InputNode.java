package pl.javasoft.recognizeletter;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class InputNode implements Node {
    private long x;
    private long y;
    private int value;



    @Override
    public void calc() {

    }

    @Override
    public long get() {
        return 0;
    }
}

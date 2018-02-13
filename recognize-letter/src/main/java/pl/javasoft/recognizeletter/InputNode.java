package pl.javasoft.recognizeletter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputNode implements Node {

    private double a;

    public InputNode(long x, long y, Integer integer) {
        a = integer;
        log.info("Input value: {}", integer);
    }


    @Override
    public void calc(ActivateFunction activateFunction) {

    }

    @Override
    public double get() {
        return a;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InputNode{");
        sb.append("a=").append(a);
        sb.append('}');
        return sb.toString();
    }
}

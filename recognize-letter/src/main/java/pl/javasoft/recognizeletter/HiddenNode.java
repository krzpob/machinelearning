package pl.javasoft.recognizeletter;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class HiddenNode extends RegullarNode implements Node{




    public HiddenNode(int size, Node[] input) {
        super(size);
        a = Stream.of(input).mapToDouble(Node::get).toArray();
        log.info("input: {}", (Object) w);
    }



    @Override
    public void calc(ActivateFunction activateFunction) {

        double product = product();

        result = activateFunction.activation(product);
    }

    @Override
    public double get() {
        return result;
    }

    @Override
    public void teach(double[] delta) {
        for(int i=0;i<w.length;i++){
            double sum=0;
            for (int j =0; j<delta.length;j++){
                sum+=Math.pow(delta[j]-w[i],2);
            }
            w[i]-=sum/26;
        }
    }
}

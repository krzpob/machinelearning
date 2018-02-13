package pl.javasoft.recognizeletter;

import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

public class ArrayUtilsTest {
    @Test
    public void product() throws Exception {
        //given
        double[] a = new double[]{0.5,0,0};


        then(ArrayUtils.product(a,a)).isEqualTo(0.5*0.5);
    }

}
package pl.javasoft.recognizeletter;

import com.codepoetics.protonpack.StreamUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class RecognizeLetterApplication implements CommandLineRunner {

	static final int COLS = 8;
	static final int ROWS = 12;
	static final int HIDDEN_MESH_SIZE=40;

	public static class SigmoidalUnipolar implements ActivateFunction {

		@Override
		public double activation(double x) {
			return 1/(1+Math.exp(-x));
		}
	}

	public static class Gauss implements ActivateFunction{

		double a,b,c;

		public Gauss(double a, double b, double c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public double activation(double x) {
			double frac = 2*c*c;
			double num = Math.pow((x-b),2);
			return a*Math.exp(num/frac);
		}
	}

	private SigmoidalUnipolar sigmoidalUnipolar = new SigmoidalUnipolar();
	private Gauss gauss = new Gauss(1,0,1);

	public static void main(String[] args) {
		SpringApplication.run(RecognizeLetterApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		if(args.length<1){
			printHelp();
			return;
		}
		log.info("File: {}", args[0]);
		File input = new File(args[0]);
		FileInputStream fileInputStream = new FileInputStream(input);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		InputNode[] inputNodes = readInputNodes(bufferedReader);
		log.info("input as array: {}", (Object) inputNodes);

		HiddenNode[] hiddenMesh = new HiddenNode[HIDDEN_MESH_SIZE];
		prepareHiddenLayer(hiddenMesh, inputNodes);

		for(int i=0;i<HIDDEN_MESH_SIZE;i++){
			log.info("hidden out: {}", hiddenMesh[i].get());
		}
		NormalNode[] normalMesh = new NormalNode[26];

		double[] output = new double[26];

		prepareLastLayer(hiddenMesh, normalMesh, output);


		log.info("Output: {}", output);

		double[] pattern = new double[26];
		Arrays.fill(pattern,0.0);
		pattern[0]=1;
		log.info("Delta: {}", ArrayUtils.diff(output, pattern));
		for (int i=0;i<HIDDEN_MESH_SIZE;i++){
			hiddenMesh[i].teach(ArrayUtils.diff(output, pattern));
			hiddenMesh[i].calc(gauss);
		}

		for(int i=0;i<26;i++){
			normalMesh[i].setA(hiddenMesh);
			normalMesh[i].calc(x->1.0);
			output[i]=normalMesh[i].get();
		}

		log.info("Output2: {}", output);
		log.info("Delta2: {}", ArrayUtils.diff(output, pattern));
	}

	private void prepareLastLayer(HiddenNode[]  hiddenMesh, NormalNode[] normalMesh, double[] output) {
		for(int i=0;i<26;i++){
			NormalNode normalNode = new NormalNode(40, hiddenMesh);
			normalNode.calc(gauss);
			normalMesh[i]=normalNode;
			output[i]=normalNode.get();
		}
	}

	private void prepareHiddenLayer(HiddenNode[] hiddenMesh, Node[] input) {
		for(int i=0;i<40;i++){
			HiddenNode hiddenNode = new HiddenNode(96, input);
			hiddenNode.calc(sigmoidalUnipolar);
			hiddenMesh[i]=hiddenNode;

		}
	}

	private InputNode[] readInputNodes(BufferedReader bufferedReader) {
		InputNode[] inputNodes = new InputNode[COLS * ROWS];

		StreamUtils.zipWithIndex(bufferedReader.lines()).forEach(line -> {
			log.info("y: {}", line.getIndex());
			StreamUtils.zipWithIndex(Stream.of(line.getValue().split(""))).forEach(
					position -> {
						long index = COLS*line.getIndex()+position.getIndex();
						inputNodes[(int) index] = new InputNode(position.getIndex(), line.getIndex(), Integer.valueOf(position.getValue()));
					}
			);
		});
		return inputNodes;
	}



	private void printHelp(){
		System.err.println("plik wejściowy jako pierwszy argument!!!");
	}
}

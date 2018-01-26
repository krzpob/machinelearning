package pl.javasoft.recognizeletter;

import com.codepoetics.protonpack.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class RecognizeLetterApplication implements CommandLineRunner {

	static final int COLS = 8;
	static final int ROWS = 12;
	static final int HIDDEN_MESH_SIZE=40;

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
		log.info("wagi: {}", (Object) weightGenerator());

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

	private int[][] weightGenerator(){
		int[][] result = new int[COLS*ROWS][HIDDEN_MESH_SIZE];


		IntStream.range(0, COLS*ROWS).forEach(ic-> result[ic]=IntStream.generate(()-> (int)(Math.random()*20)).limit(HIDDEN_MESH_SIZE).toArray());

		return result;
	}

	private void printHelp(){
		System.err.println("plik wejściowy jako pierwszy argument!!!");
	}
}

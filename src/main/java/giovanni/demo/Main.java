package giovanni.demo;

import java.io.File;
import java.io.IOException;

import giovanni.demo.parser.InputParser;

public class Main {
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("Parameter inputFile missing");
			printUsage();
			return;
		}

		File inputFile = new File(args[0]);
		if (!inputFile.exists()) {
			System.out.println("Invalid path for inputFile parameter");
			printUsage();
		}
		
		InputParser inputParser;
		try {
			inputParser = new InputParser(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		inputParser.printResultToConsole();
		inputParser.printResultToFile();
		
		VisualChecker.check();
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("java -jar demo.jar inputFile");
		System.out.println(" - inputFile: path to file containing South African mobile numbers");
	}
}

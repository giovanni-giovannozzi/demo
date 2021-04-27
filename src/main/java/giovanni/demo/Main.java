package giovanni.demo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import giovanni.demo.parser.InputParser;

public class Main {
	/**
	 * This application processes a csv file containing phone numbers and checks for correctness of the phone numbers.
	 * The output of the processing is printed in standard output and in three files (one for accepted numbers, one for
	 * incorrect numbers and one for corrected numbers), placed in the same directory where input file resides. Afterwards
	 * a form allows to check the correctness of a number chosen at will.
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String args[]) throws UnsupportedEncodingException {
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

	/**
	 * Prints the usage of the application
	 */
	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("java -jar demo.jar inputFile");
		System.out.println(" - inputFile: path to file containing South African mobile numbers");
	}
}

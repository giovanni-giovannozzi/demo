package giovanni.demo.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * This class is used to parse the input file
 * @author giovanni
 *
 */
public class InputParser {
	private String heading = null;
	
	private ArrayList<CheckResult> acceptableNumbers = new ArrayList<>();
	private ArrayList<CheckResult> correctedNumbers = new ArrayList<>();
	private ArrayList<CheckResult> incorrectNumbers = new ArrayList<>();
	
	private File directory = null;
	
	/**
	 * Creates a new InputParser
	 * @param inputFile the file to be processed
	 * @throws IOException thrown if the inputFile can not be read
	 */
	public InputParser(File inputFile) throws IOException {
		final List<String> lines = FileUtils.readLines(inputFile, "UTF-8");
		
		this.directory = inputFile.getParentFile();
		
		if(lines.size() == 0) {
			return;
		}
		this.heading = lines.get(0);
		lines.remove(0);
		
		if(lines.size() == 0) {
			return;
		}
		
		for(String line: lines) {
			CheckResult cr = CheckResult.checkNumber(line);
			
			switch(cr.getStatus()) {
			case ACCEPTABLE:
				acceptableNumbers.add(cr);
				break;
			case INCORRECT:
				incorrectNumbers.add(cr);
				break;
			case CORRECTED:
				correctedNumbers.add(cr);
				break;
			}
		}
	}
	
	/**
	 * It is responsible for the printing to the console of the three categories of check results
	 */
	public void printResultToConsole() {
		printConsole("Acceptable numbers", this.heading, this.acceptableNumbers);
		printConsole("Corrected numbers", this.heading + ",Correction", this.correctedNumbers);
		printConsole("Incorrect numbers", this.heading, this.incorrectNumbers);
	}
	
	/**
	 * Prints to console a list of CheckResult
	 * @param title The title to give to the paragraph
	 * @param heading The heading of the csv list
	 * @param list The list of CheckResult
	 */
	private static void printConsole(String title, String heading, ArrayList<CheckResult> list) {
		System.out.println(title);
		print(heading, list, System.out);
		System.out.println();
	}
	
	/**
	 * It is responsible for the printing to files of the three categories of check results
	 * @throws UnsupportedEncodingException
	 */
	public void printResultToFile() throws UnsupportedEncodingException {
		printFile(this.directory, "acceptableNumbers.csv", this.acceptableNumbers, this.heading);
		printFile(this.directory, "correctedNumbers.csv", this.correctedNumbers, this.heading + ",Correction");
		printFile(this.directory, "incorrectNumbers.csv", this.incorrectNumbers, this.heading);
	}
	
	/**
	 * Prints to file a list of CheckResult
	 * @param directory directory containing the file
	 * @param name name of the file to be created
	 * @param list list of CheckResult to be inserted in the file 
	 * @param heading heading of the csv file
	 * @throws UnsupportedEncodingException
	 */
	private static void printFile(File directory, String name, ArrayList<CheckResult> list, String heading) throws UnsupportedEncodingException {
		File outputFile = new File(directory, name);
		try {
			print(heading, list, new PrintStream(outputFile, "UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints a list of CheckResult to a print stream
	 * @param heading heading of the print
	 * @param list list to be printed
	 * @param out destination of the printing
	 */
	private static void print(String heading, ArrayList<CheckResult> list, PrintStream out) {
		out.println(heading);
		for(CheckResult cr: list) {
			final String correction = cr.getCorrection();
			String output = cr.getLine();
			if(correction != null) {
				output = output + "," + correction;
			}
			out.println(output);
		}
	}
}

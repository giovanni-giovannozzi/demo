package giovanni.demo.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class InputParser {
	private String heading = null;
	
	private ArrayList<CheckResult> acceptableNumbers = new ArrayList<>();
	private ArrayList<CheckResult> correctedNumbers = new ArrayList<>();
	private ArrayList<CheckResult> incorrectNumbers = new ArrayList<>();
	
	private File directory = null;
	
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
	
	public void printResultToConsole() {
		printConsole("Acceptable numbers", this.heading, this.acceptableNumbers);
		printConsole("Corrected numbers", this.heading + ",Correction", this.correctedNumbers);
		printConsole("Incorrect numbers", this.heading, this.incorrectNumbers);
	}
	
	private static void printConsole(String title, String heading, ArrayList<CheckResult> list) {
		System.out.println(title);
		print(heading, list, System.out);
		System.out.println();
	}
	
	public void printResultToFile() {
		printFile(this.directory, "acceptableNumbers.csv", this.acceptableNumbers, this.heading);
		printFile(this.directory, "correctedNumbers.csv", this.correctedNumbers, this.heading + ",Correction");
		printFile(this.directory, "incorrectNumbers.csv", this.incorrectNumbers, this.heading);
	}
	
	private static void printFile(File directory, String name, ArrayList<CheckResult> list, String heading) {
		File outputFile = new File(directory, name);
		try {
			print(heading, list, new PrintStream(outputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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

package giovanni.demo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author giovanni
 * Contains the result of the check on the number
 *
 */
public class CheckResult {
	private String line;
	private Status status;
	private String correction;
	private String number;
	
	private static final Pattern pattern = Pattern.compile("\\d+");

	/**
	 * Creates a new CheckResult
	 * @param line contains the string to put in the output file
	 * @param status the status of the check
	 * @param correction eventual correction applied to the number
	 * @param number number extracted from the line
	 */
	private CheckResult(String line, Status status, String correction, String number) {
		super();
		this.line = line;
		this.status = status;
		this.correction = correction;
		this.number = number;
	}

	public String getLine() {
		return line;
	}

	public Status getStatus() {
		return status;
	}

	public String getCorrection() {
		return correction;
	}
	
	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "CheckResult [line=" + line + ", status=" + status + ", correction=" + correction + ", number=" + number + "]";
	}

	/**
	 * This method takes the content of the line in input or directly a number to be checked for correctness
	 * @param line line of the input file to be processed o the number to be checked
	 * @return the result of the check
	 */
	public static CheckResult checkNumber(String line) {
		String parts[] = line.split(",");
		String number = null;
		if (parts.length < 2) {
			number = line;
		}
		else {
			number = parts[1];
		}
		Matcher matcher = pattern.matcher(number);
		int count = 0;
		String match = null;
		int start = 0, end = 0;
		while (matcher.find()) {
			count++;
			if(match != null) {
				break;
			}
			start = matcher.start();
			end = matcher.end();
			match = number.substring(start, end);
		}

		if (count > 1) {
			return new CheckResult(line, Status.INCORRECT, "Number pattern occurs more than once in the input", null);
		}
		
		if(match.length() == number.length()) {
			return new CheckResult(line, Status.ACCEPTABLE, null, match);
		}

		String correction = "";
		if (start > 0) {
			correction = number.substring(0, start);
		}
		if (number.length() > end) {
			correction = correction + number.substring(end, number.length());
		}
		return new CheckResult(parts[0] + "," + number, Status.CORRECTED, "Removed " + correction, match);
	}

}

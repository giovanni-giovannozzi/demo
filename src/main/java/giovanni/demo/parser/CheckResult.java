package giovanni.demo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import giovanni.demo.exception.InvalidLineException;

public class CheckResult {
	private String line;
	private Status status;
	private String correction;
	
	private static final Pattern pattern = Pattern.compile("\\d+");

	private CheckResult(String line, Status status, String correction) {
		super();
		this.line = line;
		this.status = status;
		this.correction = correction;
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

	@Override
	public String toString() {
		return "CheckResult [line=" + line + ", status=" + status + ", correction=" + correction + "]";
	}

	public static CheckResult checkNumber(String line) throws InvalidLineException {
		String parts[] = line.split(",");
		if (parts.length < 2) {
			throw new InvalidLineException(line);
		}
		final String number = parts[1];
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
			return new CheckResult(line, Status.INCORRECT, null);
		}
		
		if(match.length() == number.length()) {
			return new CheckResult(line, Status.ACCEPTABLE, null);
		}

		String correction = "";
		if (start > 0) {
			correction = number.substring(0, start);
		}
		if (number.length() > end) {
			correction = correction + number.substring(end, number.length());
		}
		return new CheckResult(parts[0] + "," + number, Status.CORRECTED, "Removed " + correction);
	}

}

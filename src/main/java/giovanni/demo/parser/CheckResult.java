package giovanni.demo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckResult {
	private String line;
	private Status status;
	private String correction;
	private String number;
	
	private static final Pattern pattern = Pattern.compile("\\d+");

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
			return new CheckResult(line, Status.INCORRECT, null, null);
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

package giovanni.demo.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CheckResultTest {
	@Test
	public void checkAcceptableNumber() {
		CheckResult cr = CheckResult.checkNumber("84528784843");
		assertEquals(cr.getStatus(), Status.ACCEPTABLE);
		assertEquals(cr.getNumber(), "84528784843");
		assertEquals(cr.getCorrection(), null);
	}
	
	@Test
	public void checkIncorrectNumber() {
		CheckResult cr = CheckResult.checkNumber("639156553262_DELETED_1486721886");
		assertEquals(cr.getStatus(), Status.INCORRECT);
		assertEquals(cr.getNumber(), null);
		assertEquals(cr.getCorrection(), null);
	}
	
	@Test
	public void checkCorrectedNumber() {
		CheckResult cr = CheckResult.checkNumber("_DELETED_1488176172");
		assertEquals(cr.getStatus(), Status.CORRECTED);
		assertEquals(cr.getNumber(), "1488176172");
		assertEquals(cr.getCorrection(), "Removed _DELETED_");
	}
}

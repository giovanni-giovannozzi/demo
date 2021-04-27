package giovanni.demo.parser;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class InputParserTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void printResultToFileTest() throws IOException {
		final String inputFileName = "input.csv";
		final ClassLoader cl = InputParserTest.class.getClassLoader();
		InputStream is = cl.getResourceAsStream(inputFileName);
		final File inputFile = tempFolder.newFile(inputFileName);
		IOUtils.copyLarge(is, new FileOutputStream(inputFile));
		
		InputParser ip = new InputParser(inputFile);
		ip.printResultToFile();
		
		File tempFolderRoot = tempFolder.getRoot();
		
		final String acceptableNumbersFileName = "acceptableNumbers.csv";
		is = cl.getResourceAsStream(acceptableNumbersFileName);
		File outputFile = new File(tempFolderRoot, acceptableNumbersFileName);
		assertArrayEquals(IOUtils.toByteArray(is), FileUtils.readFileToByteArray(outputFile));
		
		final String correctedNumbersFileName = "correctedNumbers.csv";
		is = cl.getResourceAsStream(correctedNumbersFileName);
		outputFile = new File(tempFolderRoot, correctedNumbersFileName);
		assertArrayEquals(IOUtils.toByteArray(is), FileUtils.readFileToByteArray(outputFile));
		
		final String incorrectNumbersFileName = "incorrectNumbers.csv";
		is = cl.getResourceAsStream(incorrectNumbersFileName);
		outputFile = new File(tempFolderRoot, incorrectNumbersFileName);
		assertArrayEquals(IOUtils.toByteArray(is), FileUtils.readFileToByteArray(outputFile));
	}
}

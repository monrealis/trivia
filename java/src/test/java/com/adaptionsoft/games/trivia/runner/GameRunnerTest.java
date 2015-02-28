package com.adaptionsoft.games.trivia.runner;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameRunnerTest {
	
	private static final Path masterFile = Paths.get(".", "master.txt");
	

	//@BeforeClass
	public static void createMAsterFile() throws IOException {
		Files.write(masterFile, playLotsOfGame().getBytes());
	}
	
	public static String playLotsOfGame() {
		ByteArrayOutputStream stream = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		
		for (int i = 1; i < 1000; i++) {
			GameRunner.play(new Random(i));
			System.out.println("==================================");
		}
		
		return stream.toString();
	}

	@Test
	public void test() throws IOException {
		String expected = String.join("\n", Files.readAllLines(masterFile)) + "\n";
		String actual = playLotsOfGame();
		
		assertEquals(expected, actual);
		
	}

}

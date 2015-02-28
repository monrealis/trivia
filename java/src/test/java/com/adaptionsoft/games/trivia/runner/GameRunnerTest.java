package com.adaptionsoft.games.trivia.runner;

import static java.lang.String.join;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameRunnerTest {
	private static final Path masterFile = Paths.get(".", "master.txt");

	@BeforeClass
	public static void createMasterFile() throws IOException {
		if (!shouldGenerateMasterFile())
			return;
		write(masterFile, playLotsOfGames().getBytes());
	}

	private static boolean shouldGenerateMasterFile() {
		return false;
	}

	public static String playLotsOfGames() {
		return captureSystemOut(() -> playLotsOfGames(1000));
	}

	private static String captureSystemOut(Runnable runnable) {
		PrintStream oldOut = System.out;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		runnable.run();
		System.setOut(oldOut);
		return stream.toString();
	}

	private static void playLotsOfGames(int n) {
		for (int i = 1; i < n; i++)
			playOneGame(i);
	}

	private static void playOneGame(int randomSeed) {
		GameRunner.play(new Random(randomSeed));
		System.out.println("==================================");
	}

	@Test
	public void outputUnchangedFromPreviousVersion() throws IOException {
		String expected = join("\n", readAllLines(masterFile)) + "\n";
		String actual = playLotsOfGames();
		assertEquals(expected, actual);
	}
}

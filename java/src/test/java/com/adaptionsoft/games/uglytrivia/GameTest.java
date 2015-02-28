package com.adaptionsoft.games.uglytrivia;

import static java.lang.String.join;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private static final String OTHER_NAME = "other name";
	private static final String NAME = "name";
	private ByteArrayOutputStream stream;
	private WhiteBoxGame game = new WhiteBoxGame();
	private PrintStream originalOut;

	@Before
	public void before() {
		originalOut = System.out;
		capture();
	}

	@After
	public void after() {
		System.setOut(originalOut);
		originalOut.print(stream.toString());
	}

	@Test
	public void afterConstruction_printsNothing() {
		capture();
		new Game();
		assertOutput("");
	}

	@Test
	public void afterNameIsAdded_logIsPrinted() {
		game.add(NAME);
		assertLines("name was added", "They are player number 1");
	}

	@Test
	public void afterNamesAreAdded_howManyPlayersReturnsNumberOfPlayers() {
		prepareGameOfTwo();
		assertEquals(2, game.howManyPlayers());
	}

	@Test
	public void ifLessThanTwoPlayers_notPlayable() {
		game.add(NAME);
		assertFalse(game.isPlayable());
	}

	@Test
	public void ifTwoPlayersOrMore_playable() {
		prepareGameOfTwo();
		assertTrue(game.isPlayable());
	}

	@Test
	public void createRockQuestion_returnsQuestionWithIndex() {
		assertEquals("Rock Question 5", game.createRockQuestion(5));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void afterConstruction_rollThrowsException() {
		game.roll(0);
	}

	@Test
	public void roll_logsSeveralLines() {
		prepareGameOfTwo();
		capture();
		game.roll(0);
		String[] expected = { "name is the current player",
				"They have rolled a 0", "name's new location is 0",
				"The category is Pop", "Pop Question 0" };
		assertLines(expected);
	}

	@Test
	public void afterWrongAnswer_sendsPlayerToPenaltyBox() {
		prepareGameOfTwo();
		capture();
		game.wrongAnswer();
		assertLines("Question was incorrectly answered",
				"name was sent to the penalty box");
	}

	@Test
	public void afterCorrectAnswer_playerGetsOneCoin() {
		prepareGameOfTwo();
		capture();
		game.wasCorrectlyAnswered();
		assertLines("Answer was correct!!!!", "name now has 1 Gold Coins.");
	}

	@Test
	public void afterTwoCorrectAnswers_secondPlayerHasOneGoldenCoin() {
		prepareGameOfTwo();
		game.wasCorrectlyAnswered();
		capture();
		game.wasCorrectlyAnswered();
		assertLines("Answer was correct!!!!",
				"other name now has 1 Gold Coins.");
	}

	private void assertLines(String... lines) {
		assertOutput(join("\n", lines) + "\n");
	}

	private void assertOutput(String expected) {
		assertEquals(expected, stream.toString());
	}

	private void prepareGameOfTwo() {
		game.add(NAME);
		game.add(OTHER_NAME);
	}

	private void capture() {
		stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
	}
}

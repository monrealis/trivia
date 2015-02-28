package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Game {
	ArrayList<String> players = new ArrayList<>();
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];
	private Questions questions = new Questions(50);

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	public Game() {

	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {

		players.add(playerName);
		places[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

		println(playerName + " was added");
		println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		println(players.get(currentPlayer) + " is the current player");
		println("They have rolled a " + roll);

		if (isCurrentPlayerInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				println(players.get(currentPlayer)
						+ " is getting out of the penalty box");
				places[currentPlayer] = currentPlace() + roll;
				if (currentPlace() > 11)
					places[currentPlayer] = currentPlace() - 12;

				println(players.get(currentPlayer) + "'s new location is "
						+ currentPlace());
				println("The category is " + currentCategory());
				askQuestion();
			} else {
				println(players.get(currentPlayer)
						+ " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			places[currentPlayer] = currentPlace() + roll;
			if (currentPlace() > 11)
				places[currentPlayer] = currentPlace() - 12;

			println(players.get(currentPlayer) + "'s new location is "
					+ currentPlace());
			println("The category is " + currentCategory());
			askQuestion();
		}

	}

	private void askQuestion() {
		println(getQuestion());
	}

	private String getQuestion() {
		return questions.getByCategory(currentCategory()).removeFirst();
	}

	private Category currentCategory() {
		return new CategoriesByPlace().get(currentPlace());
	}

	private int currentPlace() {
		return places[currentPlayer];
	}

	public boolean wasCorrectlyAnswered() {
		if (isCurrentPlayerInPenaltyBox() && isGettingOutOfPenaltyBox) {
			println("Answer was correct!!!!");
			purses[currentPlayer]++;
			println(players.get(currentPlayer) + " now has "
					+ purses[currentPlayer] + " Gold Coins.");
			boolean winner = didPlayerWin();
			switchToNextPlayer();

			return winner;
		} else if (isCurrentPlayerInPenaltyBox() && !isGettingOutOfPenaltyBox) {
			switchToNextPlayer();
			return true;
		} else {

			println(getCorrectAnswerText());
			purses[currentPlayer]++;
			println(players.get(currentPlayer) + " now has "
					+ purses[currentPlayer] + " Gold Coins.");

			boolean winner = didPlayerWin();
			switchToNextPlayer();

			return winner;
		}
	}

	private boolean isCurrentPlayerInPenaltyBox() {
		return inPenaltyBox[currentPlayer];
	}

	private String getCorrectAnswerText() {
		return "Answer was correct!!!!";
	}

	public boolean wrongAnswer() {
		println("Question was incorrectly answered");
		println(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		switchToNextPlayer();
		return true;
	}

	private void switchToNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
	}

	protected void println(Object text) {
		System.out.println(text);
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	public String createRockQuestion(int index) {
		return questions.createRockQuestion(index);
	}
}

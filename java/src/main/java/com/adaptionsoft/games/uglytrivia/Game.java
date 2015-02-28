package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	ArrayList<String> players = new ArrayList<>();
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

	LinkedList<String> popQuestions = new LinkedList<>();
	LinkedList<String> scienceQuestions = new LinkedList<>();
	LinkedList<String> sportsQuestions = new LinkedList<>();
	LinkedList<String> rockQuestions = new LinkedList<>();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	public Game() {
		for (int i = 0; i < 50; i++)
			addQuestion(i);
	}

	private void addQuestion(int index) {
		popQuestions.addLast("Pop Question " + index);
		scienceQuestions.addLast(("Science Question " + index));
		sportsQuestions.addLast(("Sports Question " + index));
		rockQuestions.addLast(createRockQuestion(index));
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
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

		if (inPenaltyBox[currentPlayer]) {
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
		if (currentCategory() == Category.Pop)
			println(popQuestions.removeFirst());
		if (currentCategory() == Category.Science)
			println(scienceQuestions.removeFirst());
		if (currentCategory() == Category.Sports)
			println(sportsQuestions.removeFirst());
		if (currentCategory() == Category.Rock)
			println(rockQuestions.removeFirst());
	}

	private Category currentCategory() {
		if (currentPlace() == 0)
			return Category.Pop;
		if (currentPlace() == 4)
			return Category.Pop;
		if (currentPlace() == 8)
			return Category.Pop;
		if (currentPlace() == 1)
			return Category.Science;
		if (currentPlace() == 5)
			return Category.Science;
		if (currentPlace() == 9)
			return Category.Science;
		if (currentPlace() == 2)
			return Category.Sports;
		if (currentPlace() == 6)
			return Category.Sports;
		if (currentPlace() == 10)
			return Category.Sports;
		return Category.Rock;
	}

	private int currentPlace() {
		return places[currentPlayer];
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				println("Answer was correct!!!!");
				purses[currentPlayer]++;
				println(players.get(currentPlayer) + " now has "
						+ purses[currentPlayer] + " Gold Coins.");
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;
				return true;
			}

		} else {

			println(getCorrectAnswerText());
			purses[currentPlayer]++;
			println(players.get(currentPlayer) + " now has "
					+ purses[currentPlayer] + " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size())
				currentPlayer = 0;

			return winner;
		}
	}

	private String getCorrectAnswerText() {
		return "Answer was correct!!!!";
	}

	public boolean wrongAnswer() {
		println("Question was incorrectly answered");
		println(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	protected void println(Object text) {
		System.out.println(text);
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}

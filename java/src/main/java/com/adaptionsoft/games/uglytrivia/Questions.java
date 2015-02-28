package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Questions {
	LinkedList<String> popQuestions = new LinkedList<>();
	LinkedList<String> scienceQuestions = new LinkedList<>();
	LinkedList<String> sportsQuestions = new LinkedList<>();
	LinkedList<String> rockQuestions = new LinkedList<>();
	Map<Category, LinkedList<String>> questions = new HashMap<>();

	public Questions(int n) {
		for (int i = 0; i < n; i++)
			addQuestion(i);
		questions.put(Category.Pop, popQuestions);
		questions.put(Category.Science, scienceQuestions);
		questions.put(Category.Sports, sportsQuestions);
		questions.put(Category.Rock, rockQuestions);
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
	
	public LinkedList<String> getByCategory(Category c) {
		return questions.get(c);
	}
}

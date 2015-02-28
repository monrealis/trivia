package com.adaptionsoft.games.uglytrivia;

import static java.util.Arrays.stream;

import java.util.Map;
import java.util.TreeMap;

public class CategoriesByPlace {
	private Category defaultValue = Category.Rock;
	private Map<Integer, Category> categories = new TreeMap<>();

	public CategoriesByPlace() {
		add(Category.Pop, 0, 4, 8);
		add(Category.Science, 1, 5, 9);
		add(Category.Sports, 2, 6, 10);
	}

	private void add(Category c, int... places) {
		stream(places).forEach((i) -> categories.put(i, c));
	}

	public Category get(int currentPlace) {
		Category r = categories.get(currentPlace);
		if (r != null)
			return r;
		return defaultValue;
	}

}

package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class WhiteBoxGame extends Game {
	private List<String> lines = new ArrayList<>();

	@Override
	protected void println(Object text) {
		lines.add(text.toString());
		super.println(text);
	}

	public String getJoinedLines() {
		if (lines.isEmpty())
			return "";
		return String.join("\n", lines) + "\n";
	}
	
	public void reset() {
		lines.clear();
	}
}

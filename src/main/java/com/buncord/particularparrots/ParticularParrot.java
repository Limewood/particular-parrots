package com.buncord.particularparrots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ParticularParrot {
	Phoenix(
			2,
			"phoenix wright",
			"phoenix",
			"ryunosuke naruhodo",
			"ryunosuke",
			"naruhodo",
			"odo",
			"runo"
	),
	Apollo(2, "apollo"),
	Heimdall(2, "heimdall");

	public final List<String> names;
	/** Probability of playing random custom sound, 1 in {probability} */
	public final int probability;

	ParticularParrot(int probability, String...nickNames) {
		this.probability = probability;
		this.names = new ArrayList<>();
		this.names.addAll(Arrays.asList(nickNames));
	}
}

package eu.t6nn.gester.operations;

import java.util.Queue;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;

public interface SelectionStrategy {
	Queue<Identity> select(Population population, int pairCount);
}

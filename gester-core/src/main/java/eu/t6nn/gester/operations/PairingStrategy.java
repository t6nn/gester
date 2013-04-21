package eu.t6nn.gester.operations;

import java.util.Queue;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;

public interface PairingStrategy extends OperationStrategy {
	Queue<Identity> pair(Population population, int pairCount);
}

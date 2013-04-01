package eu.t6nn.gester.operations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.utils.MutableBitBuffer;

public class NonElitistRandomMutationStrategy implements MutationStrategy {

	private Random random = new Random();
	
	private double bottomRatio;
	private double mutationRatio;

	public NonElitistRandomMutationStrategy(double bottomRatio, double mutationRatio) {
		this.bottomRatio = bottomRatio;
		this.mutationRatio = mutationRatio;
	}
	
	@Override
	public void mutate(Population population) {
		int mutable = (int) (bottomRatio * population.size());
		int mutationCount = (int) (mutable * mutationRatio * population.identitySize());
		
		int[] keys = new int[mutationCount];
		for(int i = 0; i < mutationCount; ++i) {
			keys[i] = population.size() - mutable + random.nextInt(mutable);
		}
		Arrays.sort(keys);
		
		List<Identity> mutatedIdentities = new LinkedList<>();
		
		int lastkey = -1;
		MutableBitBuffer lastState = null;
		for (int i = mutationCount - 1; i >= 0; --i) {
			int key = keys[i];
			if(key != lastkey) {
				if(lastState != null) {
					// Remove the initial identity and perform the mutation 
					mutatedIdentities.add(population.remove(lastkey).clone(lastState));
				}
				lastkey = key;
				lastState = population.get(key).encode();
			}
			// flip a random bit. TODO: avoid flipping the same bit twice.
			int bitIndex = random.nextInt(population.identitySize());
			lastState.set(bitIndex, lastState.get(bitIndex) == 0 ? 1 : 0);
		}
		
		// Add the new identities back to the population.
		for (Identity identity : mutatedIdentities) {
			population.add(identity);
		}
	}

}

package eu.t6nn.gester.operations.impl;

import java.util.Arrays;
import java.util.Collection;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.operations.MatingStrategy;
import eu.t6nn.gester.utils.BitBuffer;

public abstract class BitmapCrossoverMatingStrategy implements MatingStrategy {
	
	protected abstract BitBuffer generateCrossover(int size);
	
	@Override
	public Collection<Identity> mate(Identity id1, Identity id2) {
		assert id1.size() == id2.size();
		
		BitBuffer crossover = generateCrossover(id1.size());
		assert crossover.size() == id1.size();
		
		BitBuffer state1 = id1.encode();
		BitBuffer state2 = id2.encode();
		
		for(int i = 0; i < crossover.size(); ++i) {
			// Cross over the bits where crossover[n] == 0
			if(crossover.get(i) == 0) {
				int bit = state1.get(i);
				state1.set(i, state2.get(i));
				state2.set(i, bit);
			}
		}
		
		return Arrays.asList(id1.clone(state1), id2.clone(state2));
	}
}

package eu.t6nn.gester.operations;

import java.util.Random;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.IdentityDef;
import eu.t6nn.gester.MapIdentity;
import eu.t6nn.gester.utils.MutableBitBuffer;

public class RandomInitializationStrategy implements InitializationStrategy {

	private Random random = new Random();
	
	@Override
	public Identity initialize(IdentityDef idDef) {
		Identity blank = new MapIdentity(idDef);
		
		MutableBitBuffer buffer = blank.encode();
		for(int i = 0; i < buffer.size(); ++i) {
			buffer.set(i, random.nextInt(2));
		}
		
		return blank.clone(buffer);
	}

}

package eu.t6nn.gester.operations;

import java.util.Collection;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.MapIdentity;
import eu.t6nn.gester.MapIdentityDef;
import eu.t6nn.gester.utils.MutableBitBuffer;
import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class CrossoverMatingStrategyTest {
	@Test
	public void testMateRandom() {
		verify(new RandomCrossoverMatingStrategy());
	}
	
	@Test
	public void testMateSinglePoint() {
		verify(new SinglePointCrossoverMatingStrategy());
	}
	
	private void verify(MatingStrategy str) {
		MapIdentityDef def = new MapIdentityDef();
		def.addTrait("v1", new GrayEncodedIntegerVariable(0, 10));
		
		MapIdentity blank = new MapIdentity(def);

		Identity id1 = blank.clone(blank.encode()); // all bits should be 0;
		MutableBitBuffer ones = blank.encode();
		for (int i = 0; i < ones.size(); ++i) {
			ones.set(i, 1);
		}
		Identity id2 = blank.clone(ones); // all bits should be 1

		System.out.println(id1.encode());
		System.out.println(id2.encode());

		Collection<Identity> ids = str.mate(id1, id2);
		Assert.assertEquals(ids.size(), 2);

		Iterator<Identity> idIter = ids.iterator();

		MutableBitBuffer buf1 = idIter.next().encode();
		MutableBitBuffer buf2 = idIter.next().encode();

		System.out.println(buf1);
		System.out.println(buf2);

		Assert.assertTrue(buf1.size() == buf2.size(),
				"Identities should have the same size.");
		for (int i = 0; i < buf1.size(); ++i) {
			Assert.assertTrue((buf1.get(i) ^ buf2.get(i)) == 1,
					"Bits in the identity chromosomes match for i = " + i);
		}
	}
}

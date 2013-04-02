package eu.t6nn.gester;


import org.testng.Assert;
import org.testng.annotations.Test;

import eu.t6nn.gester.utils.MutableBitBuffer;

public class MapIdentityTest {
	
	@Test
	public void testInitialization() {
		MapIdentity identity = new MapIdentity();
		
		GrayEncodedLongVariable var1 = new GrayEncodedLongVariable(0, 2);
		GrayEncodedLongVariable var2 = new GrayEncodedLongVariable(0, 100);
		
		identity.addTrait("var1", var1);
		identity.addTrait("var2", var2);
		
		Assert.assertEquals(identity.getTrait("var1"), var1);
		Assert.assertEquals(identity.getTrait("var2"), var2);
	}
	
	@Test
	public void testClone() {
		MapIdentity identity = new MapIdentity();
		
		GrayEncodedLongVariable var1 = new GrayEncodedLongVariable(0, 2);
		identity.addTrait("var1", var1);
		
		MutableBitBuffer buf = identity.encode();
		for(int i = 0; i < buf.size(); ++i) {
			buf.set(i, 1);
		}
		Identity id2 = identity.clone(buf);
		
		Assert.assertEquals(id2.encode(), buf);
	}
}

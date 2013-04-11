package eu.t6nn.gester;


import org.testng.Assert;
import org.testng.annotations.Test;

import eu.t6nn.gester.utils.BitBuffer;
import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class MapIdentityTest {
	
	@Test
	public void testInitialization() {
		MapIdentityDef identityDef = new MapIdentityDef();
		
		GrayEncodedIntegerVariable var1 = new GrayEncodedIntegerVariable(0, 2);
		GrayEncodedIntegerVariable var2 = new GrayEncodedIntegerVariable(0, 100);
		
		identityDef.addTrait("var1", var1);
		identityDef.addTrait("var2", var2);
		
		MapIdentity identity = new MapIdentity(identityDef);
		Assert.assertEquals(identity.getTrait("var1"), var1);
		Assert.assertEquals(identity.getTrait("var2"), var2);
	}
	
	@Test
	public void testClone() {
		MapIdentityDef idDef = new MapIdentityDef();
		
		GrayEncodedIntegerVariable var1 = new GrayEncodedIntegerVariable(0, 2);
		idDef.addTrait("var1", var1);
		
		MapIdentity identity = new MapIdentity(idDef);
		BitBuffer buf = identity.encode();
		for(int i = 0; i < buf.size(); ++i) {
			buf.set(i, 1);
		}
		Identity id2 = identity.clone(buf);
		
		Assert.assertEquals(id2.encode(), buf);
	}
}

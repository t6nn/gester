package eu.t6nn.gester.operations;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.testng.annotations.Test;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.operations.impl.RepeatedBestPerformersConvergenceStrategy;

public class RepeatedBestPerformersConvergenceStrategyTest {
	
	@Test
	public void testConvergence() {
	
		RepeatedBestPerformersConvergenceStrategy strategy = new RepeatedBestPerformersConvergenceStrategy(1, 3);
		
		Identity id1 = mockId();
		Identity id2 = mockId();
		
		Population pop1 = mockPopulation(id1, id2);
		Population pop2 = mockPopulation(id2, id1);
		
		Assert.assertFalse(strategy.detect(pop1, 1)); // first time seen
		Assert.assertFalse(strategy.detect(pop2, 2));
		Assert.assertFalse(strategy.detect(pop2, 3));
		Assert.assertFalse(strategy.detect(pop1, 1)); // second time seen
		Assert.assertTrue(strategy.detect(pop1, 1)); // third time seen
	}
	
	private Population mockPopulation(Identity ... ids) {
		Population pop = EasyMock.createMock(Population.class);
		EasyMock.expect(pop.size()).andStubReturn(ids.length);
		int n = 0;
		for (Identity identity : ids) {
			EasyMock.expect(pop.get(n)).andStubReturn(identity);
			n++;
		}
		EasyMock.replay(pop);
		return pop;
	}
	
	private Identity mockId() {
		Identity id = EasyMock.createMock(Identity.class);
		EasyMock.replay(id);
		return id;
	}
}

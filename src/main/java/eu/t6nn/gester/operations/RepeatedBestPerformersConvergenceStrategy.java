package eu.t6nn.gester.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;

public class RepeatedBestPerformersConvergenceStrategy extends
		AbstractConvergenceDetectionStrategy {

	private int topNPerformers;
	private int nTimes;
	
	private Map<Identity, AtomicInteger> appearanceCounts = new HashMap<Identity, AtomicInteger>();
	
	public RepeatedBestPerformersConvergenceStrategy(int topNPerformers, int nTimes) {
		assert nTimes > 1 : "Best performer count should be larger than 0";
		this.topNPerformers = topNPerformers;
		this.nTimes = nTimes;
	}
	
	@Override
	public boolean detect(Population pop, int genIndex) {
		for(int i = 0; i < Math.min(topNPerformers, pop.size()); i++) {
			Identity id = pop.get(i);
			AtomicInteger count = appearanceCounts.get(id);
			if(count == null) {
				appearanceCounts.put(id, new AtomicInteger(1));
			} else {
				if(count.incrementAndGet() >= nTimes) {
					return true;
				}
			}
		}
		return false;
	}
}

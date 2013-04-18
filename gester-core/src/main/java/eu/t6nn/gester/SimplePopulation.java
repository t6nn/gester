package eu.t6nn.gester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.list.TreeList;

public class SimplePopulation implements Population {

	private Random random = new Random();

	private ExecutorService populationPool;

	@SuppressWarnings("unchecked")
	List<Identity> identities = new TreeList();

	private int idSize;

	private TestCase testCase;

	public SimplePopulation(TestCase testCase, IdentityDef idDef, int populationSize, int threadPoolSize) {
		this.testCase = testCase;
		this.idSize = idDef.size();

		if(threadPoolSize == 1) {
			this.populationPool = Executors.newSingleThreadExecutor();
		} else {
			this.populationPool = Executors.newFixedThreadPool(threadPoolSize);
		}
	}

	@Override
	public int identitySize() {
		return idSize;
	}

	@Override
	public int size() {
		return identities.size();
	}

	@Override
	public Identity remove(int i) {
		return identities.remove(i);
	}

	@Override
	public Identity get(int i) {
		return identities.get(i);
	}

	@Override
	public void add(Identity id) {
		if (id.size() != idSize) {
			throw new IllegalArgumentException(
					"Unsuitable identity - expected size " + idSize
							+ " but was " + id.size());
		}
		identities.add(id);
	}

	/**
	 * Re-calculates identity costs.
	 */
	@Override
	public void update() {
		final long cacheKey = random.nextLong();

		List<Future<?>> futures = new ArrayList<>();
		for (Identity identity : identities) {
			futures.add(populationPool.submit(new IdentityUpdater(testCase, identity,
					cacheKey)));
		}
		
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		
		Collections.sort(identities, new Comparator<Identity>() {

			@Override
			public int compare(Identity o1, Identity o2) {
				return Double.compare(o1.test(testCase, cacheKey),
						o2.test(testCase, cacheKey));
			}
		});
	}

	private final static class IdentityUpdater implements Runnable {

		private TestCase testCase;
		private Identity identity;
		private long cacheKey;

		public IdentityUpdater(TestCase testCase, Identity identity,
				long cacheKey) {
			this.testCase = testCase;
			this.identity = identity;
			this.cacheKey = cacheKey;
		}

		@Override
		public void run() {
			identity.test(testCase, cacheKey);
		}

	}

}

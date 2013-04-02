package eu.t6nn.gester;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.list.TreeList;

public class PopulationImpl implements Population {

	private Random random = new Random();

	@SuppressWarnings("unchecked")
	List<Identity> identities = new TreeList();
	
	private int idSize;

	private TestCase testCase;

	public PopulationImpl(TestCase testCase, int populationSize) {
		this.testCase = testCase;
		this.idSize = testCase.newIdentity().encode().size();
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
		Collections.sort(identities, new Comparator<Identity>() {

			@Override
			public int compare(Identity o1, Identity o2) {
				return Double.compare(o1.cachedCost(testCase, cacheKey),
						o2.cachedCost(testCase, cacheKey));
			}
		});
	}

}

package eu.t6nn.gester;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;

import eu.t6nn.gester.utils.MutableBitBuffer;

public class MapIdentity implements Identity {

	private OrderedMap traits = new LinkedMap();
	private int totalSize = 0;

	private double cachedCost = 0.0D;
	private long cacheKey = -1;

	@SuppressWarnings("unchecked")
	public void addTrait(String name, Variable trait) {
		traits.put(name, trait);
		totalSize += trait.size();
	}

	@Override
	public Variable getTrait(String name) {
		return (Variable) traits.get(name);
	}

	@Override
	public MutableBitBuffer encode() {
		MapIterator it = traits.mapIterator();
		MutableBitBuffer bits = new MutableBitBuffer(totalSize);
		while (it.hasNext()) {
			it.next();
			Variable value = (Variable) it.getValue();
			bits.append(value.encode(), value.size());
		}
		return bits;
	}

	@Override
	public Identity clone(MutableBitBuffer newState) {
		MapIterator it = traits.mapIterator();
		MapIdentity identity = new MapIdentity();
		int ptr = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			Variable value = (Variable) it.getValue();
			int size = value.size();

			identity.addTrait(key, value.clone(newState.range(ptr, size)));
			ptr += size;
		}

		return identity;
	}

	@Override
	public int size() {
		return totalSize;
	}

	@Override
	public double cachedCost(TestCase source, long key) {
		if (cacheKey != key || cacheKey == -1) {
			cachedCost = source.calculateCost(this);
			cacheKey = key;
		}
		return cachedCost;
	}

}

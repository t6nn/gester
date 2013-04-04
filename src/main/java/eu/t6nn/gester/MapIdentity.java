package eu.t6nn.gester;

import java.util.Map;

import eu.t6nn.gester.utils.MutableBitBuffer;
import eu.t6nn.gester.variables.Variable;

public class MapIdentity implements Identity {

	private final IdentityDef idDef;
	private MutableBitBuffer encoded;

	private double cachedCost = 0.0D;
	private long cacheKey = -1;
	
	private Map<String, Variable> decodeCache;

	public MapIdentity(IdentityDef idDef) {
		this.idDef = idDef;
		this.encoded = new MutableBitBuffer(idDef.size());
	}

	@Override
	public Variable getTrait(String name) {
		if(decodeCache == null) {
			decodeCache = idDef.decode(encoded);
		}
		return (Variable) decodeCache.get(name);
	}

	@Override
	public MutableBitBuffer encode() {
		return encoded;
//		
//		MapIterator it = traits.mapIterator();
//		MutableBitBuffer bits = new MutableBitBuffer(totalSize);
//		while (it.hasNext()) {
//			it.next();
//			Variable value = (Variable) it.getValue();
//			bits.append(value.encode(), value.size());
//		}
//		return bits;
	}

	@Override
	public Identity clone(MutableBitBuffer newState) {
		MapIdentity identity = new MapIdentity(idDef);
		identity.encoded = this.encoded.clone();

		return identity;
	}

	@Override
	public int size() {
		return idDef.size();
	}

	@Override
	public double test(TestCase source, long testRun) {
		if (cacheKey != testRun || cacheKey == -1) {
			cachedCost = source.test(this);
			cacheKey = testRun;
		}
		return cachedCost;
	}

}

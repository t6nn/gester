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
		return (Variable) decode().get(name);
	}
	
	private Map<String, Variable> decode() {
		if(decodeCache == null) {
			decodeCache = idDef.decode(encoded);
		}
		return decodeCache;
	}

	@Override
	public MutableBitBuffer encode() {
		return encoded;
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
	
	@Override
	public String toString() {
		return decode().toString();
	}

}

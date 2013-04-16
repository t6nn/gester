package eu.t6nn.gester;

import java.util.Map;
import eu.t6nn.gester.exceptions.GesterException;
import eu.t6nn.gester.utils.BitBuffer;
import eu.t6nn.gester.variables.Variable;

public class MapIdentity implements Identity
{

	private final IdentityDef idDef;
	private BitBuffer encoded;

	private double cachedCost = 0.0D;
	private long cacheKey = -1;

	private Map<String, Variable> decodeCache;

	public MapIdentity (IdentityDef idDef) {
		this.idDef = idDef;
		this.encoded = new BitBuffer(idDef.size());
	}

	@SuppressWarnings ("unchecked")
	@Override
	public <T extends Variable> T getTrait (String name) {
		return (T) decode().get(name);
	}

	private Map<String, Variable> decode () {
		if (decodeCache == null) {
			decodeCache = idDef.decode(encoded);
		}
		return decodeCache;
	}

	@Override
	public BitBuffer encode () {
		return encoded.clone();
	}

	@Override
	public Identity clone (BitBuffer newState) {
		MapIdentity identity = new MapIdentity(idDef);
		identity.encoded = newState.clone();

		return identity;
	}

	@Override
	public int size () {
		return idDef.size();
	}

	@Override
	public double test (TestCase source, long testRun) {
		if (cacheKey != testRun || cacheKey == -1) {
			try {
				cachedCost = source.test(this);
				cacheKey = testRun;
			} catch (Exception e) {
				throw new GesterException(e);
			}
		}
		return cachedCost;
	}

	@Override
	public String toString () {
		return "cost=" + cachedCost + " value=" + decode().toString();
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encoded == null) ? 0 : encoded.hashCode());
		result = prime * result + ((idDef == null) ? 0 : idDef.hashCode());

		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MapIdentity other = (MapIdentity) obj;
		if (encoded == null) {
			if (other.encoded != null) {
				return false;
			}
		} else if (!encoded.equals(other.encoded)) {
			return false;
		}
		if (idDef == null) {
			if (other.idDef != null) {
				return false;
			}
		} else if (!idDef.equals(other.idDef)) {
			return false;
		}
		return true;
	}

	@Override
	public double lastCost() {
		return cachedCost;
	}

}

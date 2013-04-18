package eu.t6nn.gester;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;
import eu.t6nn.gester.utils.BitBuffer;
import eu.t6nn.gester.variables.Variable;

public class MapIdentityDef implements IdentityDef {

	private OrderedMap traits = new LinkedMap();
	private int totalSize = 0;

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Variable> Trait<T> addTrait(String name, Variable trait) {
		traits.put(name, trait);
		totalSize += trait.size();
		
		return new NamedTrait<T>(name, (Class<T>)trait.getClass());
	}

	@Override
	public Map<String, Variable> decode(BitBuffer buffer) {
		Map<String, Variable> decoded = new HashMap<>(traits.size());
		MapIterator it = traits.mapIterator();
		int ptr = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			Variable value = (Variable) it.getValue();
			int size = value.size();
			decoded.put(key, value.clone(buffer.range(ptr, size)));
			ptr += size;
		}

		return Collections.unmodifiableMap(decoded);
	}

	@Override
	public int size() {
		return totalSize;
	}

}

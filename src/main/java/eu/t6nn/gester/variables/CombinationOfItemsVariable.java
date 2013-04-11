package eu.t6nn.gester.variables;

import java.util.ArrayList;
import java.util.Collection;

import eu.t6nn.gester.utils.MutableBitBuffer;


public class CombinationOfItemsVariable<T> implements Variable
{

	private Collection<T> values;
	private MutableBitBuffer bits;

	public CombinationOfItemsVariable (Collection<T> values) {
		this.values = values;
		this.bits = new MutableBitBuffer(values.size());
	}
	
	@Override
	public int size () {
		return values.size();
	}

	@Override
	public byte[] encode () {
		return bits.getBytes();
	}

	@Override
	public CombinationOfItemsVariable<T> clone (byte[] newState) {
		CombinationOfItemsVariable<T> var = new CombinationOfItemsVariable<>(values);
		var.bits = new MutableBitBuffer(values.size());
		var.bits.append(newState, values.size());
		return var;
	}
	
	public Collection<T> getCombination() {
		Collection<T> combination = new ArrayList<>();
		int i = 0;
		for (T value : values) {
			if(bits.get(i++) == 1) {
				combination.add(value);
			}
		}
		return combination;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bits == null) ? 0 : bits.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		CombinationOfItemsVariable<?> other = (CombinationOfItemsVariable<?>) obj;
		if (bits == null) {
			if (other.bits != null) {
				return false;
			}
		} else if (!bits.equals(other.bits)) {
			return false;
		}
		if (values == null) {
			if (other.values != null) {
				return false;
			}
		} else if (!values.equals(other.values)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString () {
		return getCombination().toString();
	}

}

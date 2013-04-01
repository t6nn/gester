package eu.t6nn.gester;

import java.util.LinkedList;
import java.util.List;

public class PopulationImpl implements Population {
	
	List<Identity> identities = new LinkedList<>();
	private int idSize;
	
	public PopulationImpl(int idSize) {
		this.idSize = idSize;
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
		if(id.size() != idSize) {
			throw new IllegalArgumentException("Unsuitable identity - expected size " + idSize + " but was " + id.size());
		}
	}
	
	
	
}

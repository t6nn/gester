package eu.t6nn.gester.operations.impl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.operations.PairingStrategy;

public class RankWeightedRandomPairing implements PairingStrategy {

	private Random random = new Random();
	
	public RankWeightedRandomPairing() {
	}
	
	@Override
	public Queue<Identity> pair(Population population, int pairCount) {
		int size = population.size();

		Queue<Identity> ids = new LinkedList<>();
		for(int i = 0; i < pairCount; ++i) {
			int p1 = pick(size), p2;
			do {
				p2 = pick(size);
			} while (p2 == p1);
			
			ids.add(population.get(p1));
			ids.add(population.get(p2));
		}
		return ids;
	}
	
	private int pick(int size) {
		double rnd = random.nextDouble();
		double cumulative = 0.0D;
		double rankTotal = (size * (1d + size) / 2d);
		
		for(int rank = 0; rank < size; rank++) {
			cumulative += (size - rank) / rankTotal;
			if(rnd < cumulative) {
				return rank;
			}
		}
		
		return size - 1;
	}

}

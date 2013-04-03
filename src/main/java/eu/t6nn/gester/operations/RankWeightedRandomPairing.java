package eu.t6nn.gester.operations;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;

public class RankWeightedRandomPairing implements PairingStrategy {

	private Random random = new Random();
	private double keepRatio;
	
	public RankWeightedRandomPairing(double keepRatio) {
		this.keepRatio = keepRatio;
	}
	
	@Override
	public Queue<Identity> pair(Population population, int pairCount) {
		int keep = (int)((double)population.size() * keepRatio);

		Queue<Identity> ids = new LinkedList<>();
		for(int i = 0; i < pairCount; ++i) {
			int p1 = pick(keep), p2;
			do {
				p2 = pick(keep);
			} while (p2 == p1);
			
			ids.add(population.get(p1));
			ids.add(population.get(p2));
		}
		return ids;
	}
	
	private int pick(int keep) {
		double rnd = random.nextDouble();
		double cumulative = 0.0D;
		double rankTotal = (keep * (1d + keep) / 2d);
		
		for(int rank = 0; rank < keep; rank++) {
			cumulative += (keep - rank) / rankTotal;
			if(rnd <= cumulative) {
				return rank;
			}
		}
		
		return keep - 1;
	}

}

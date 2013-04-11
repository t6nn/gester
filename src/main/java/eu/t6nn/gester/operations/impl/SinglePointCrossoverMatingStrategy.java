package eu.t6nn.gester.operations.impl;

import java.util.Arrays;
import java.util.Random;

import eu.t6nn.gester.utils.BitBuffer;

public class SinglePointCrossoverMatingStrategy extends BitmapCrossoverMatingStrategy {

	private Random random = new Random();
	
	@Override
	protected BitBuffer generateCrossover(int size) {
		int crossoverPoint = random.nextInt(size);
		
		BitBuffer buffer = new BitBuffer(size);
		byte[] ones = new byte[crossoverPoint / 8 + 1];
		Arrays.fill(ones, (byte)0xff);
		buffer.append(ones, crossoverPoint);
		
		return buffer;
	}

}

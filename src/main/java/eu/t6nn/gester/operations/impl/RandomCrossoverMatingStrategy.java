package eu.t6nn.gester.operations.impl;

import java.util.Random;

import eu.t6nn.gester.utils.BitBuffer;

public class RandomCrossoverMatingStrategy extends
		BitmapCrossoverMatingStrategy {

	private Random random = new Random();
	
	@Override
	protected BitBuffer generateCrossover(int size) {
		BitBuffer buffer = new BitBuffer(size);
		if(size >= 8) {
			byte[] bytes = new byte[size / 8];
			random.nextBytes(bytes);
			buffer.append(bytes, size - size % 8);
		}
		for(int i = 0; i< size % 8; ++i) {
			buffer.append(new byte[]{(byte) random.nextInt(2)}, 1);
		}
		
		return buffer;
	}

}

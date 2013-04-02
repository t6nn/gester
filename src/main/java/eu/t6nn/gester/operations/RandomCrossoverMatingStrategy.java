package eu.t6nn.gester.operations;

import java.util.Random;

import eu.t6nn.gester.utils.MutableBitBuffer;

public class RandomCrossoverMatingStrategy extends
		BitmapCrossoverMatingStrategy {

	private Random random = new Random();
	
	@Override
	protected MutableBitBuffer generateCrossover(int size) {
		MutableBitBuffer buffer = new MutableBitBuffer(size);
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

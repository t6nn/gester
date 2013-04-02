package eu.t6nn.gester.operations;

import java.util.Arrays;
import java.util.Random;

import eu.t6nn.gester.utils.MutableBitBuffer;

public class SinglePointCrossoverMatingStrategy extends BitmapCrossoverMatingStrategy {

	private Random random = new Random();
	
	@Override
	protected MutableBitBuffer generateCrossover(int size) {
		int crossoverPoint = random.nextInt(size);
		
		MutableBitBuffer buffer = new MutableBitBuffer(size);
		byte[] ones = new byte[crossoverPoint / 8 + 1];
		Arrays.fill(ones, (byte)0xff);
		buffer.append(ones, crossoverPoint);
		
		return buffer;
	}

}

package eu.t6nn.gester.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MutableBitBufferTest {
	
	@Test
	public void testAppend() {
		MutableBitBuffer buffer = new MutableBitBuffer(25); // 4 bytes
		buffer.append(new byte[]{1}, 1); // 0000 0001
		Assert.assertEquals(buffer.getBytes().length, 4);
		Assert.assertEquals(buffer.getBytes(), new byte[]{1, 0, 0, 0});
		
		buffer.append(new byte[]{2}, 2); // 0000 0101 
		Assert.assertEquals(buffer.getBytes().length, 4);
		Assert.assertEquals(buffer.getBytes(), new byte[]{5, 0, 0, 0});
		
		buffer.append(new byte[]{127}, 7); // 0000 0011 1111 1101
		Assert.assertEquals(buffer.getBytes().length, 4);
		Assert.assertEquals(buffer.getBytes(), new byte[]{(byte) 0xfd, 3, 0, 0});
	}
	
	@Test
	public void testSetGet() {
		MutableBitBuffer buffer = new MutableBitBuffer(25); // 4 bytes
		for(int i = 0; i < 25; ++i) {
			Assert.assertEquals(buffer.get(i), 0);
			buffer.set(i, 1);
			Assert.assertEquals(buffer.get(i), 1);
			buffer.set(i, 0);
			Assert.assertEquals(buffer.get(i), 0);
		}
	}
	
	@Test
	public void testRange() {
		MutableBitBuffer buffer = new MutableBitBuffer(25); // 4 bytes
		for(int i = 0; i < 25; ++i) {
			buffer.set(i, 1);
		}
		
		byte[] range = buffer.range(5, 3);
		Assert.assertEquals(range, new byte[]{7});
	}
}

package eu.t6nn.gester.variables;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CombinationOfItemsVariableTest
{

	@Test
	public void testDecode () {
		Collection<Long> items = Arrays.asList(1L, 2L, 3L, 4L, 5L);
		
		CombinationOfItemsVariable<Long> value = new CombinationOfItemsVariable<>(items);
		Assert.assertEquals(value.clone(new byte[]{0b00000}).getCombination(), Collections.emptyList());
		Assert.assertEquals(value.clone(new byte[]{0b00001}).getCombination(), Arrays.asList(1L));
		Assert.assertEquals(value.clone(new byte[]{0b00011}).getCombination(), Arrays.asList(1L, 2L));
		Assert.assertEquals(value.clone(new byte[]{0b10101}).getCombination(), Arrays.asList(1L, 3L, 5L));
	}
}

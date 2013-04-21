package eu.t6nn.gester.example.services;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import org.testng.annotations.Test;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Identity;
import eu.t6nn.gester.IdentityDef;
import eu.t6nn.gester.MapIdentityDef;
import eu.t6nn.gester.TestCase;
import eu.t6nn.gester.Trait;
import eu.t6nn.gester.example.entities.Transaction;
import eu.t6nn.gester.example.entities.TransactionId;
import eu.t6nn.gester.example.entities.TransactionIdV2;
import eu.t6nn.gester.operations.Converge;
import eu.t6nn.gester.operations.Feedback;
import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class TransactionCacheTest {

	@Test
	public void testStoreAndLoadWithRandomParameters() {

		final TransactionCache cache = new TransactionCache(100000);

		IdentityDef id = new MapIdentityDef();

		final Date startDate = new Date();

		final Trait<GrayEncodedIntegerVariable> code = id.addTrait("code", new GrayEncodedIntegerVariable(0, 100));
		/*
		 * date deltas, last 8 hours, 5 minute precision
		 */
		final Trait<GrayEncodedIntegerVariable> date = id.addTrait("date", new GrayEncodedIntegerVariable(-8 * 12, 0));
		/*
		 * 100 different sources
		 */
		final Trait<GrayEncodedIntegerVariable> sourceSuffix = id.addTrait("source", new GrayEncodedIntegerVariable(0,
				100));

		TestCase testCase = new TestCase() {

			@Override
			public double test(Identity identity) throws Exception {
				TransactionId id = new TransactionIdV2(code.var(identity).getValue(), new Date(startDate.getTime()
						+ date.var(identity).getValue() * 5 * 60000), "src" + sourceSuffix.var(identity).getValue());
				Transaction tx = new Transaction(id);
				tx.setAmount(BigDecimal.ZERO);
				tx.setDescription("");

				long start = System.nanoTime();
				cache.add(tx);
				Assert.assertEquals(cache.get(id), tx);
				return start - System.nanoTime();
			}
		};

		Gester.test(testCase, id).apply(Feedback.db("f:\\temp\\gester\\txtest").clearDatabase(true).prunedOnly(true))
				.apply(Converge.maxAge(30000)).run();

	}
}

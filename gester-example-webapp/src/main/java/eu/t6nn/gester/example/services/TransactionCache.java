package eu.t6nn.gester.example.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import eu.t6nn.gester.example.entities.Transaction;
import eu.t6nn.gester.example.entities.TransactionId;

public class TransactionCache {
	
	private final Map<TransactionId, Transaction> txCache;
	
	public TransactionCache(int expectedSize) {
		txCache = new ConcurrentHashMap<>(expectedSize);
	}
	
	public void add(Transaction tx) {
		txCache.put(tx.getId(), tx);
	}
	
	public Transaction get(TransactionId txId) {
		return txCache.get(txId);
	}
}

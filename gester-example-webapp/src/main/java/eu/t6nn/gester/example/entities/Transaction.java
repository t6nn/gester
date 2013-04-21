package eu.t6nn.gester.example.entities;

import java.math.BigDecimal;

public class Transaction {
	
	private final TransactionId id;
	
	private BigDecimal amount;
	
	private String description;

	public Transaction(TransactionId id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionId getId() {
		return id;
	}
}

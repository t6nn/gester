package eu.t6nn.gester.example.entities;


import java.util.Date;

import org.apache.commons.lang.ObjectUtils;

public class TransactionIdV2 extends TransactionIdV1 {

	private final String source;

	public TransactionIdV2(Long code, Date date, String source) {
		super(code, date);
		this.source = source;
	}

	// Implement equals, but forget to implement hashCode()
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TransactionIdV2) {
			return ObjectUtils.equals(this.source,
					((TransactionIdV2) obj).source) && super.equals(obj);
		} else {
			return false;
		}
	}

}

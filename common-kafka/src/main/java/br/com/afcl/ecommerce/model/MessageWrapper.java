package br.com.afcl.ecommerce.model;

import lombok.Getter;

@Getter
public class MessageWrapper<T> {

	private final CorrelationId correlationId;
	private final T payload;

	public MessageWrapper(CorrelationId correlationId, T payload) {
		this.correlationId = correlationId;
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "MessageWrapper{" + "correlationId=" + correlationId + ", content=" + payload + '}';
	}
}

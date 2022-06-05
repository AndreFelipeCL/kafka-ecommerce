package br.com.afcl.ecommerce.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CorrelationId {

	private final String id;

	public CorrelationId(String prefixId) {
		this.id = prefixId + "(" + UUID.randomUUID() + ")";
	}

	@Override
	public String toString() {
		return "CorrelationId{" + "id='" + id + '\'' + '}';
	}

	public CorrelationId continueWith(String suffixId) {
		return new CorrelationId(this.id + "-" + suffixId);
	}
}

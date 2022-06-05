package br.com.afcl.ecommerce;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
@Getter
public final class Order {
	
	private final String email;
	private final String orderId;
	private final BigDecimal totalAmount;
	
	public Order(String email, String orderId, BigDecimal totalAmount) {
		this.email = email;
		this.orderId = orderId;
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		return "Order{orderId='" + orderId + '\'' + ", totalAmount=" + totalAmount + '}';
	}
}

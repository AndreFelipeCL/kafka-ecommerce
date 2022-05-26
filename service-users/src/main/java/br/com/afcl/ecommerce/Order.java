package br.com.afcl.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;

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
	private final String userId;
	
	public Order(String userId, String orderId, BigDecimal totalAmount, String email) {
		this.userId = userId;
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Order{" + "userId='" + userId + '\'' + ", orderId='" + orderId + '\'' + ", totalAmount=" + totalAmount + '}';
	}
	
}

package br.com.afcl;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
@AllArgsConstructor
public final class Order {
	
	private final String orderId;
	private final BigDecimal totalAmount;
	private final String email;
}

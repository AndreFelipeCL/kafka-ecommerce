package br.com.afcl.ecommerce.models;

import java.math.BigDecimal;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
public final class Order {

    private String userId;

    private String orderId;

    private BigDecimal totalAmount;

    public Order(String userId, String orderId, BigDecimal totalAmount) {
        this.userId = userId;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }
}

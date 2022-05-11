package br.com.afcl.ecommerce.pubsub.model;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 29/04/2022
 */
public enum Constants {
    ECOMMERCE_NEW_ORDER("ECOMMERCE_NEW_ORDER"),
    ECOMMERCE_SEND_EMAIL("ECOMMERCE_SEND_EMAIL"),
    ECOMMERCE_ALL_PATTERN("ECOMMERCE.*");

    private final String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

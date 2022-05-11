package br.com.afcl.ecommerce.models;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
public final class Email {

    private final String subject;

    private final String body;

    public Email(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

}

package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneratedAuthorProvider {

    private Author generatedAuthor;

    public Author get() {
        return generatedAuthor;
    }

    public void set(Author generatedAuthor) {
        this.generatedAuthor = generatedAuthor;
    }
}

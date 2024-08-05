package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookService {

    public Book createBook(Author author) {
        return new Book(author);
    }
}

package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository {

    public Author createAuthor(Author author) {
        //put the Author into any database
        return author;
    }

    public void deleteAuthorWithId(Integer authorId) {
        //delete Author from database
    }
}

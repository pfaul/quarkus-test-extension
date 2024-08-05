package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@ExtendWith(AuthorGeneratorExtension.class)
class BookServiceTest {

    @Inject
    BookService bookService;

    @Test
    void testCreateBook(@GeneratedAuthor Author author) {
        Book book = bookService.createBook(author);
        assertEquals(author.id(), book.author().id());
    }

}
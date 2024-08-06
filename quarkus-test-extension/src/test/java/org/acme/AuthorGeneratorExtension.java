package org.acme;

import io.quarkus.test.junit.callback.QuarkusTestAfterAllCallback;
import io.quarkus.test.junit.callback.QuarkusTestBeforeClassCallback;
import io.quarkus.test.junit.callback.QuarkusTestContext;
import jakarta.enterprise.inject.spi.CDI;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class AuthorGeneratorExtension implements QuarkusTestBeforeClassCallback, QuarkusTestAfterAllCallback, ParameterResolver {

    @Override
    public void beforeClass(Class<?> testClass) {
        CDI<Object> cdi = CDI.current();
        IdGenerator idGenerator = cdi.select(IdGenerator.class).get();
        AuthorRepository authorRepository = cdi.select(AuthorRepository.class).get();
        GeneratedAuthorProvider authorProvider = cdi.select(GeneratedAuthorProvider.class).get();

        Author generatedAuthor = authorRepository.createAuthor(new Author(idGenerator.generateId(), "My favorite author"));
        authorProvider.set(generatedAuthor);
        System.out.println("created author with id " + generatedAuthor.id());
    }

    @Override
    public void afterAll(QuarkusTestContext context) {
        CDI<Object> cdi = CDI.current();
        AuthorRepository authorRepository = cdi.select(AuthorRepository.class).get();
        GeneratedAuthorProvider authorProvider = cdi.select(GeneratedAuthorProvider.class).get();

        Author generatedAuthor = authorProvider.get();
        authorRepository.deleteAuthorWithId(generatedAuthor.id());
        System.out.println("deleted author with id " + generatedAuthor.id());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(GeneratedAuthor.class)
                && parameterContext.getParameter().getType().equals(Author.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        CDI<Object> cdi = CDI.current();
        GeneratedAuthorProvider authorProvider = cdi.select(GeneratedAuthorProvider.class).get();
        return authorProvider.get();
    }
}

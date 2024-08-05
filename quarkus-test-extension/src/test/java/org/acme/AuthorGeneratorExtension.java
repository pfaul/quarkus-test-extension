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

    private AuthorRepository authorRepository;
    private Author generatedAuthor;

    @Override
    public void beforeClass(Class<?> testClass) {
        CDI<Object> cdi = CDI.current();
        IdGenerator idGenerator = cdi.select(IdGenerator.class).get();
        authorRepository = cdi.select(AuthorRepository.class).get();

        generatedAuthor = authorRepository.createAuthor(new Author(idGenerator.generateId(), "My favorite author"));
    }

    @Override
    public void afterAll(QuarkusTestContext context) {
        CDI<Object> cdi = CDI.current();
        authorRepository = cdi.select(AuthorRepository.class).get();
        authorRepository.deleteAuthorWithId(generatedAuthor.id());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(GeneratedAuthor.class)
                && parameterContext.getParameter().getType().equals(Author.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return generatedAuthor;
    }
}

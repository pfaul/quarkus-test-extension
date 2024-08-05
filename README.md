This is an example project to demonstrate the usage of ParameterResolver in a QuarkusTest.

The example is kept simple. 

We have book and author as two entities.

AuthorRepository should depict the database layer for authors.

BookService is the class under test.

Start with looking at BookServiceTest.

The challenge is, to create an easy-to-use JUnit extension, so that an author can be generated for multiple different tests.

To make things a bit more complicated, the IdGenerator is an application scoped bean, that needs to be accessed in the extension scope.

While the code looks promising, the test sadly fails.

Using the debugger I was able to find out, that even though using the same class AuthorGeneratorExtension has different instances during the test run.

The field generatedAuthor is not persisted between the different lifecycles of BeforeClass, AfterAll and resolveParameter.

This leads to two problems: 

1) The cleanup of the AuthorRepository in the AfterAll test phase is not possible
2) The author is null in the test method, since it can not be resolved
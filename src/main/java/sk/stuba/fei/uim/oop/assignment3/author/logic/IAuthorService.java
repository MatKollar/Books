package sk.stuba.fei.uim.oop.assignment3.author.logic;

import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();

    Author createAuthor(AuthorRequest request);

    Author getAuthorById(Long id) throws NotFoundException;

    Author updateAuthor(Long id, AuthorUpdateRequest request) throws NotFoundException;

    void addBookToAuthor(Long authorId, Long bookId) throws NotFoundException;

    void deleteBookFromAuthor(long authorID, long bookID) throws NotFoundException;

    void deleteAuthor(Long id) throws NotFoundException;
}

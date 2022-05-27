package sk.stuba.fei.uim.oop.assignment3.book.logic;

import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();

    Book createBook(BookRequest request) throws NotFoundException;

    void returnBook(Book book) throws NotFoundException;

    Book getBookById(long id) throws NotFoundException;

    Book updateBook(long id, BookUpdateRequest request) throws NotFoundException;

    void deleteBook(long id) throws NotFoundException;

    int getAmount(long id) throws NotFoundException;

    long addAmount(long id, long increment) throws NotFoundException;

    int getLendCount(long id) throws NotFoundException;
}

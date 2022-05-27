package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public List<Author> getAllAuthors() {
        return this.repository.findAll();
    }

    @Override
    public Author createAuthor(AuthorRequest request) {
        return this.repository.save(new Author(request));
    }

    @Override
    public Author getAuthorById(Long id) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if (a == null) {
            throw new NotFoundException();
        }
        return a;
    }

    @Override
    public Author updateAuthor(Long id, AuthorUpdateRequest request) throws NotFoundException {
        Author a = this.getAuthorById(id);
        if (request.getName() != null) {
            a.setName(request.getName());
        }
        if (request.getSurname() != null) {
            a.setSurname(request.getSurname());
        }
        return this.repository.save(a);
    }

    @Override
    public void addBookToAuthor(Long authorId, Long bookId) throws NotFoundException {
        Author a = this.getAuthorById(authorId);
        Book book = bookService.getBookById(bookId);
        if (bookNotInAuthorBooks(a, bookId)) {
            a.getBooks().add(book);
        }
        this.repository.save(a);
    }

    private boolean bookNotInAuthorBooks(Author author, long bookId) {
        for (Book book : author.getBooks()) {
            if (book.getId() == bookId) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void deleteBookFromAuthor(long authorId, long bookId) throws NotFoundException{
        Author a = this.getAuthorById(authorId);
        if (a.getBooks().size() > 0) {
            for (int i = 0; i < a.getBooks().size(); i++) {
                if (a.getBooks().get(i).getId() == bookId) {
                    a.getBooks().remove(i);
                    break;
                }
            }
            this.repository.save(a);
        }
    }

    @Override
    public void deleteAuthor(Long id) throws NotFoundException {
        Author a = this.getAuthorById(id);
        for (int i = a.getBooks().size(); i > 0; i--) {
            this.bookService.deleteBook(a.getBooks().get(i-1).getId());
        }
        this.repository.delete(this.getAuthorById(id));
    }
}

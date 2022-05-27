package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository repository;

    @Autowired
    private IAuthorService authorService;

    @Override
    public List<Book> getAllBooks() {
        return this.repository.findAll();
    }

    @Override
    public Book createBook(BookRequest request) throws NotFoundException {
        Book b = this.repository.save(new Book(request));
        this.authorService.addBookToAuthor(request.getAuthor(), b.getId());
        return b;
    }

    @Override
    public void returnBook(Book book) throws NotFoundException{
        book.setLendCount(book.getLendCount()-1);
        this.authorService.addBookToAuthor(book.getAuthor(), book.getId());
    }

    @Override
    public Book getBookById(long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b;
    }

    @Override
    public Book updateBook(long id, BookUpdateRequest request) throws NotFoundException {
        Book b = this.getBookById(id);
        if (request.getName() != null) {
            b.setName(request.getName());
        }
        if (request.getDescription() != null) {
            b.setDescription(request.getDescription());
        }
        if (request.getAuthor() != null && request.getAuthor() != 0L) {
            b.setAuthor(request.getAuthor());
        }
        if (request.getPages() > 0) {
            b.setPages(request.getPages());
        }
        return this.repository.save(b);
    }

    @Override
    public void deleteBook(long id) throws NotFoundException {
        long authorId = this.getBookById(id).getAuthor();
        this.authorService.deleteBookFromAuthor(authorId, id);
        this.repository.delete(this.getBookById(id));
    }

    @Override
    public int getAmount(long id) throws NotFoundException {
        return this.getBookById(id).getAmount();
    }

    @Override
    public int getLendCount(long id) throws NotFoundException {
        return this.getBookById(id).getLendCount();
    }

    @Override
    public long addAmount(long id, long increment) throws NotFoundException {
        Book b = this.getBookById(id);
        b.setAmount((int) (b.getAmount() + increment));
        this.repository.save(b);
        return b.getAmount();
    }
}

package sk.stuba.fei.uim.oop.assignment3.list.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendList;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendListRepository;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.ListEntry;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.ListEntryBody;

import java.util.List;

@Service
public class LendListService implements ILendListService {

    @Autowired
    private LendListRepository repository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IListEntryService listEntryService;

    @Override
    public List<LendList> getAllLendLists() {
        return this.repository.findAll();
    }

    @Override
    public LendList createList() {
        return this.repository.save(new LendList());
    }

    @Override
    public LendList getListById(long id) throws NotFoundException {
        LendList list = this.repository.findLendListById(id);
        if (list == null) {
            throw new NotFoundException();
        }
        return list;
    }

    @Override
    public void deleteLendList(long id) throws NotFoundException {
        LendList list = this.getListById(id);
        if (list.isLended()) {
            this.returnBooksToLibrary(list);
        }
        this.repository.delete(this.getListById(id));
    }

    private void returnBooksToLibrary(LendList list) throws NotFoundException {
        for (ListEntry entry : list.getLendingList()) {
            this.bookService.returnBook(entry.getBook());
        }
    }

    @Override
    public LendList addBookToList(long id, ListEntryBody body) throws NotFoundException , IllegalOperationException {
        LendList list = this.getListById(id);
        this.isLended(list);
        this.bookIsThere(list, body.getId());
        ListEntry listEntry = listEntryService.createEntry();
        listEntry.setBook(bookService.getBookById(body.getId()));
        list.getLendingList().add(listEntryService.save(listEntry));
        return this.repository.save(list);
    }

    @Override
    public void deleteBook(long listId) {
        LendList list = this.repository.findLendListById(listId);
        list.getLendingList().remove(0);
    }

    @Override
    public void lendList(long listId) throws NotFoundException, IllegalOperationException {
        LendList list = this.getListById(listId);
        this.isLended(list);
        list.setLended(true);
        this.updateLendingCount(list);
        this.repository.save(list);
    }

    private void isLended(LendList list) throws IllegalOperationException {
        if (list.isLended()) {
            throw new IllegalOperationException();
        }
    }

    private void bookIsThere(LendList list, long bookId) throws IllegalOperationException {
        for (ListEntry entry : list.getLendingList()) {
            if (entry.getBook().getId() == bookId) {
                throw new IllegalOperationException();
            }
        }
    }

    private void updateLendingCount(LendList list) {
        for (ListEntry entry : list.getLendingList()) {
            entry.getBook().setLendCount(entry.getBook().getLendCount()+1);
        }
    }
}

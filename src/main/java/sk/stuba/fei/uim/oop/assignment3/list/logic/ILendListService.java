package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.list.data.LendList;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.ListEntryBody;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface ILendListService {

    List<LendList> getAllLendLists();

    LendList createList();

    LendList getListById(long id) throws NotFoundException;

    void deleteLendList(long id) throws NotFoundException;

    LendList addBookToList(long id, ListEntryBody body) throws NotFoundException, IllegalOperationException;

    void deleteBook(long listId);

    void lendList(long listId) throws NotFoundException, IllegalOperationException;
}

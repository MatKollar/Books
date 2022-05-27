package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.list.data.ListEntry;

public interface IListEntryService {

    ListEntry createEntry();

    ListEntry save(ListEntry listEntry);
}

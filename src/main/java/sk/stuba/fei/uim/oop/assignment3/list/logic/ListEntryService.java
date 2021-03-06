package sk.stuba.fei.uim.oop.assignment3.list.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.list.data.ListEntry;
import sk.stuba.fei.uim.oop.assignment3.list.data.ListEntryRepository;

@Service
public class ListEntryService implements IListEntryService {

    @Autowired
    ListEntryRepository repository;

    @Override
    public ListEntry createEntry() {
        return this.repository.save(new ListEntry());
    }

    @Override
    public ListEntry save(ListEntry listEntry) {
        return this.repository.save(listEntry);
    }
}

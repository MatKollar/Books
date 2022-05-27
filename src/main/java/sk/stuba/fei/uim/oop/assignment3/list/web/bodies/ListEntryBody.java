package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.list.data.ListEntry;

@Getter
@Setter
@NoArgsConstructor
public class ListEntryBody {

    private long id;
    private String name;
    private String description;
    private Long author;
    private int pages;
    private int amount;
    private int lendCount;

    public ListEntryBody(ListEntry listEntry) {
        this.id = listEntry.getBook().getId();
        this.name = listEntry.getBook().getName();
        this.description = listEntry.getBook().getDescription();
        this.author = listEntry.getBook().getAuthor();
        this.pages = listEntry.getBook().getPages();
        this.amount = listEntry.getBook().getAmount();
        this.lendCount = listEntry.getBook().getLendCount();
    }
}

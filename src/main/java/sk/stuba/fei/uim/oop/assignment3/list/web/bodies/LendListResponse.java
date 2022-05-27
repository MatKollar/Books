package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LendListResponse {

    private final long id;
    private final List<ListEntryBody> lendingList;
    private final boolean lended;

    public LendListResponse(LendList list) {
        this.id = list.getId();
        this.lended = list.isLended();
        this.lendingList = list.getLendingList().stream().map(ListEntryBody::new).collect(Collectors.toList());
    }
}

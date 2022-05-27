package sk.stuba.fei.uim.oop.assignment3.list.data;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class LendList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<ListEntry> lendingList;

    private boolean lended;

    public LendList() {
        this.lendingList = new ArrayList<>();
    }
}

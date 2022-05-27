package sk.stuba.fei.uim.oop.assignment3.list.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;

import sk.stuba.fei.uim.oop.assignment3.list.logic.ILendListService;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.LendListResponse;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.ListEntryBody;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendListController {

    @Autowired
    private ILendListService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LendListResponse> getAllLists() {
        return this.service.getAllLendLists().stream().map(LendListResponse::new).collect(Collectors.toList());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendListResponse> addList() {
        return new ResponseEntity<>(new LendListResponse(this.service.createList()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LendListResponse getList(@PathVariable("id") long listId) throws NotFoundException {
        return new LendListResponse(this.service.getListById(listId));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteList(@PathVariable("id") long listId) throws NotFoundException {
        this.service.deleteLendList(listId);
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LendListResponse addBookToList(@PathVariable("id") Long listId, @RequestBody ListEntryBody listEntry) throws NotFoundException, IllegalOperationException {
        return new LendListResponse(this.service.addBookToList(listId, listEntry));
    }

    @DeleteMapping(value = "/{id}/remove")
    public void deleteBook(@PathVariable("id") long listId) {
        this.service.deleteBook(listId);
    }

    @GetMapping(value = "/{id}/lend")
    public void lendList(@PathVariable("id") long listId) throws NotFoundException, IllegalOperationException {
        this.service.lendList(listId);
    }
}

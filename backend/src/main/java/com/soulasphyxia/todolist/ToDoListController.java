package com.soulasphyxia.todolist;
import com.soulasphyxia.todolist.model.ToDoList;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/lists")
@AllArgsConstructor
public class ToDoListController {

    private final ToDoListService toDoListService;

    @GetMapping
    ResponseEntity<List<ToDoList>> getAllToDoLists() {
        return new ResponseEntity<>(toDoListService.allLists(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ToDoList> getSingleToDoList(@PathVariable Long id) {
        return new ResponseEntity<>(toDoListService.singleList(id),HttpStatus.OK);
    }

    @PostMapping("/add")
    ResponseEntity<ToDoList> addSingleList(@RequestBody ToDoList list) {
        return new ResponseEntity<>(toDoListService.add(list), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ToDoList> deleteSingleList(@PathVariable long id) {
        return new ResponseEntity<>(toDoListService.delete(id), HttpStatus.OK);
    }


    @PutMapping("/update")
    ResponseEntity<ToDoList> updateSingleList(@RequestBody ToDoList list){
        return new ResponseEntity<>(toDoListService.update(list),HttpStatus.OK);
    }


    @ExceptionHandler({NoToDoListException.class})
    public ResponseEntity<?> noToDoListHandler(NoToDoListException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

}






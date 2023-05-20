package uz.sudev.usermanagementsystembackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.sudev.usermanagementsystembackend.entity.User;
import uz.sudev.usermanagementsystembackend.payload.Message;
import uz.sudev.usermanagementsystembackend.payload.UserDTO;
import uz.sudev.usermanagementsystembackend.service.interfaces.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getUsers(page, size);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Message> addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Message> editUser(@RequestBody @Valid UserDTO userDTO, @PathVariable Long id) {
        return userService.editUser(id, userDTO);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Message>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Message> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Message message = new Message(false, error.getDefaultMessage());
            messages.add(message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
    }
}

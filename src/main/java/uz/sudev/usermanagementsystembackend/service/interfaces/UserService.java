package uz.sudev.usermanagementsystembackend.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.sudev.usermanagementsystembackend.entity.User;
import uz.sudev.usermanagementsystembackend.payload.Message;
import uz.sudev.usermanagementsystembackend.payload.UserDTO;

public interface UserService {
    ResponseEntity<Page<User>> getUsers(int page, int size);

    ResponseEntity<?> getUser(Long id);

    ResponseEntity<Message> addUser(UserDTO userDTO);

    ResponseEntity<Message> editUser(Long id, UserDTO userDTO);

    ResponseEntity<Message> deleteUser(Long id);
}

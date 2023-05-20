package uz.sudev.usermanagementsystembackend.service.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.usermanagementsystembackend.entity.User;
import uz.sudev.usermanagementsystembackend.payload.Message;
import uz.sudev.usermanagementsystembackend.payload.UserDTO;
import uz.sudev.usermanagementsystembackend.repository.UserRepository;
import uz.sudev.usermanagementsystembackend.service.interfaces.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Page<User>> getUsers(int page, int size) {
        return ResponseEntity.ok(userRepository.findAll(PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Unfortunately, the user has not been found!"));
        }
    }

    @Override
    public ResponseEntity<Message> addUser(UserDTO userDTO) {
        if (!userRepository.existsByEmail(userDTO.getEmail())) {
            if (!userRepository.existsByFirstNameAndLastName(userDTO.getFirstName(), userDTO.getLastName())) {
                userRepository.save(new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The user has been successfully created!"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The first name and last name has been already registered!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The email is being used by another user!"));
        }
    }

    @Override
    public ResponseEntity<Message> editUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!userRepository.existsByEmailAndIdNot(userDTO.getEmail(), user.getId())) {
                if (!userRepository.existsByFirstNameAndLastNameAndIdNot(userDTO.getFirstName(), userDTO.getLastName(), user.getId())) {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    userRepository.save(user);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The current user has been successfully updated!"));
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The current new first name and last name have been already registered!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The current new email is being used by another user!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Unfortunately, the user is not found!"));
        }
    }

    @Override
    public ResponseEntity<Message> deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The user has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Unfortunately, the user has not been found!"));
        }
    }
}

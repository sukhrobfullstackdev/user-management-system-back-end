package uz.sudev.usermanagementsystembackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {
    @NotNull(message = "The first name cannot be null!")
    @NotBlank(message = "The first name cannot be blank!")
    @Length(min = 3, max = 20, message = "The first name should contain minimum 3-20 letters")
    private String firstName;
    @NotNull(message = "The last name cannot be null!")
    @NotBlank(message = "The last name cannot be blank!")
    @Length(min = 5,max = 22, message = "The first name should contain 5-22 letters")
    private String lastName;
    @NotNull(message = "The email cannot be null!")
    @NotBlank(message = "The email cannot be blank!")
    @Length(min = 7,max = 50, message = "The email should contain 7-50 letters")
    @Email
    private String email;
}

package pl.sda.mysampleblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateAdded = LocalDateTime.now();
    @NotBlank(message = "This field cannot be empty")
    private String name;
    @Email(message = "Invalid email address")
    @NotBlank(message = "This field cannot be empty")
    private String email;
    @NotBlank(message = "This field cannot be empty")
    @Size(min = 9, message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "This field cannot be empty")
    @Type(type = "text" )
    private String message;
    private boolean status = false;

    public Contact(String name, String email, String phoneNumber, String message) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }
}

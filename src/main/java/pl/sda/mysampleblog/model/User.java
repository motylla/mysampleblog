package pl.sda.mysampleblog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email(message = "Invalid email address")
    @NotBlank(message = "This field cannot be empty")
    private String email;
    @NotBlank(message = "This field cannot be empty")
    @Size(min=6, message = "Password must contain at least six characters")
    private String password;
    private LocalDateTime registered = LocalDateTime.now();
    private boolean activityFlag = false;
    @Transient //pamięć podręczna bez wpisywania do bazy danych
    @NotBlank(message = "This field cannot be empty")
    @Size(min=6, message = "Password must contain at least six characters")
    private String password_confirm;


    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void subRole(Role role) {
        this.roles.remove(role);
    }


}

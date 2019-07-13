package pl.sda.mysampleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysampleblog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);
}

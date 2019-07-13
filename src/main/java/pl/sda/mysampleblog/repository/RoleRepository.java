package pl.sda.mysampleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysampleblog.model.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
}

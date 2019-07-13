package pl.sda.mysampleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysampleblog.model.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    //wartość z search = %value%
    List<Contact> findAllByEmailLikeOrMessageLikeOrPhoneNumberLikeOrNameLike(String email, String message, String phoneNumber, String name);
}
